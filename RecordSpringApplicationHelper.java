package cn.linkkids.channel.goods;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;
import org.springframework.boot.context.metrics.buffering.StartupTimeline;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.metrics.StartupStep;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author qianqi
 * @date 2024/1/11 14:41
 */
public class RecordSpringApplicationHelper {
    private static Map<Long, List<Long>> ref;
    private static Map<Long, StartupTimeline.TimelineEvent> map;
    private static Set<Long> view;
    /**
     * 系统中可以保存的最大记录条数, 超过这个数的记录被舍弃
     * */
    private static int beanCount = 1000;
    /**
     * 耗时小于该值的记录将会过滤掉
     * */
    private static int filter_ms = 100;
    private static BufferingApplicationStartup bufferingApplicationStartup;

    public static ConfigurableApplicationContext run(Class<?> primarySource, String... args) {
        SpringApplication springApplication = new SpringApplication(primarySource);
        bufferingApplicationStartup = new BufferingApplicationStartup(beanCount);
        springApplication.setApplicationStartup(bufferingApplicationStartup);
        return springApplication.run(args);
    }

    public static void setBeanCount(int beanCount) {
        RecordSpringApplicationHelper.beanCount = beanCount;
    }

    public static void setFilter_ms(int filter_ms) {
        RecordSpringApplicationHelper.filter_ms = filter_ms;
    }

    public static String getResultJson(){
        if(bufferingApplicationStartup == null){
            throw new RuntimeException("Execute the run method before executing the getResultJson method.");
        }
        List<StartupTimeline.TimelineEvent> timelineEvents = bufferingApplicationStartup.drainBufferedTimeline().getEvents();
        timelineEvents = new ArrayList<>(timelineEvents);
        timelineEvents = timelineEvents.stream().filter((e)-> e.getDuration().getNano() / 1000_000 >= filter_ms).collect(Collectors.toList());
        ref = timelineEvents.stream().collect(Collectors.groupingBy(e -> e.getStartupStep().getParentId() == null ? -1 : e.getStartupStep().getParentId(),
                Collectors.mapping(e -> e.getStartupStep().getId(), Collectors.toList())));
        map = timelineEvents.stream().collect(Collectors.toMap((e) -> e.getStartupStep().getId(), e -> e));
        List<TreeTimelineEvent> list = new ArrayList<>(beanCount);
        view = new HashSet<>(beanCount);
        for (long i = 0; i < beanCount; i++) {
            if(view.contains(i)){
                continue;
            }
            TreeTimelineEvent dfs = dfs(i);
            if(dfs != null){
                list.add(dfs);
            }
        }
        list.sort((o1, o2) -> o2.getDuration().compareTo(o1.getDuration()));
        return JSON.toJSONString(list);
    }

    private static TreeTimelineEvent dfs(long id){
        StartupTimeline.TimelineEvent timelineEvent = map.get(id);
        view.add(id);
        if(timelineEvent == null){
            return null;
        }
        TreeTimelineEvent self = new TreeTimelineEvent();
        self.setId(timelineEvent.getStartupStep().getId());
        self.setParentId(timelineEvent.getStartupStep().getParentId());
        self.setName(timelineEvent.getStartupStep().getName());
        self.setStartTime(timelineEvent.getStartTime());
        self.setEndTime(timelineEvent.getEndTime());
        self.setDuration(timelineEvent.getDuration());
        self.setTags(timelineEvent.getStartupStep().getTags());
        List<Long> childIdList = ref.get(id);
        if(childIdList == null || childIdList.size() == 0){
            return self;
        }
        for (Long child : childIdList) {
            self.getChildren().add(dfs(child));
        }
        return self;
    }

    static class TreeTimelineEvent{
        @JSONField(ordinal = 1)
        private long id;
        @JSONField(ordinal = 2)
        private Long parentId;
        @JSONField(ordinal = 3)
        private Duration duration;
        @JSONField(ordinal = 4)
        private Instant startTime;
        @JSONField(ordinal = 5)
        private Instant endTime;
        @JSONField(ordinal = 6)
        private String name;
        @JSONField(ordinal = 7)
        private StartupStep.Tags tags;
        @JSONField(ordinal = 8)
        private Set<TreeTimelineEvent> children = new TreeSet<>((o1, o2) -> o2.getDuration().compareTo(o1.getDuration()));

        public long getId() {
            return id;
        }

        @Override
        public int hashCode() {
            return (int) id;
        }

        @Override
        public boolean equals(Object obj) {
            if(obj instanceof TreeTimelineEvent){
                return ((TreeTimelineEvent) obj).id == id;
            }
            return false;
        }

        public void setId(long id) {
            this.id = id;
        }

        public Long getParentId() {
            return parentId;
        }

        public void setParentId(Long parentId) {
            this.parentId = parentId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Instant getStartTime() {
            return startTime;
        }

        public void setStartTime(Instant startTime) {
            this.startTime = startTime;
        }

        public Instant getEndTime() {
            return endTime;
        }

        public void setEndTime(Instant endTime) {
            this.endTime = endTime;
        }

        public Duration getDuration() {
            return duration;
        }

        public void setDuration(Duration duration) {
            this.duration = duration;
        }

        public StartupStep.Tags getTags() {
            return tags;
        }

        public void setTags(StartupStep.Tags tags) {
            this.tags = tags;
        }

        public Set<TreeTimelineEvent> getChildren() {
            return children;
        }

        public void setChildren(Set<TreeTimelineEvent> children) {
            this.children = children;
        }
    }
}
