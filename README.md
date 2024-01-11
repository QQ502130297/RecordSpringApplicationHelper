# RecordSpringApplicationHelper
依赖SpringBoot提供的ApplicationStartup统计应用启动时的详细耗时

## 使用步骤
```java
public static void main(String[] args) {
    RecordSpringApplicationHelper.run(ChannelGoodsAdminApplication.class, args);
    System.out.println(RecordSpringApplicationHelper.getResultJson());
}
```

## 结果
```json
[
    {
        "id": 4,
        "duration": "PT28.186S",
        "startTime": "2024-01-11T06:55:36.787Z",
        "endTime": "2024-01-11T06:56:04.973Z",
        "name": "spring.context.refresh",
        "tags": [],
        "children": [
            {
                "id": 5,
                "parentId": 4,
                "duration": "PT13.148S",
                "startTime": "2024-01-11T06:55:36.800Z",
                "endTime": "2024-01-11T06:55:49.948Z",
                "name": "spring.context.beans.post-process",
                "tags": [],
                "children": [
                    {
                        "id": 8,
                        "parentId": 5,
                        "duration": "PT11.149S",
                        "startTime": "2024-01-11T06:55:36.841Z",
                        "endTime": "2024-01-11T06:55:47.990Z",
                        "name": "spring.context.beandef-registry.post-process",
                        "tags": [
                            {
                                "key": "postProcessor",
                                "value": "org.springframework.context.annotation.ConfigurationClassPostProcessor@612e21b9"
                            }
                        ],
                        "children": []
                    },
                    {
                        "id": 80,
                        "parentId": 5,
                        "duration": "PT0.481S",
                        "startTime": "2024-01-11T06:55:49.391Z",
                        "endTime": "2024-01-11T06:55:49.872Z",
                        "name": "spring.beans.instantiate",
                        "tags": [
                            {
                                "key": "beanName",
                                "value": "nacosConfigurationPropertiesBindingPostProcessor"
                            },
                            {
                                "key": "beanType",
                                "value": "interface org.springframework.beans.factory.config.BeanPostProcessor"
                            }
                        ],
                        "children": [
                            {
                                "id": 81,
                                "parentId": 80,
                                "duration": "PT0.473S",
                                "startTime": "2024-01-11T06:55:49.392Z",
                                "endTime": "2024-01-11T06:55:49.865Z",
                                "name": "spring.beans.instantiate",
                                "tags": [
                                    {
                                        "key": "beanName",
                                        "value": "org.springframework.cache.config.internalCacheAdvisor"
                                    },
                                    {
                                        "key": "beanType",
                                        "value": "interface org.springframework.aop.Advisor"
                                    }
                                ],
                                "children": []
                            }
                        ]
                    }
                ]
            }
        ]
    },
    {
        "id": 282,
        "parentId": 281,
        "duration": "PT3.938S",
        "startTime": "2024-01-11T06:55:53.425Z",
        "endTime": "2024-01-11T06:55:57.363Z",
        "name": "spring.beans.instantiate",
        "tags": [
            {
                "key": "beanName",
                "value": "backCategoryMapper"
            }
        ],
        "children": [
            {
                "id": 283,
                "parentId": 282,
                "duration": "PT3.897S",
                "startTime": "2024-01-11T06:55:53.431Z",
                "endTime": "2024-01-11T06:55:57.328Z",
                "name": "spring.beans.instantiate",
                "tags": [
                    {
                        "key": "beanName",
                        "value": "sqlSessionFactory"
                    }
                ],
                "children": [
                    {
                        "id": 291,
                        "parentId": 283,
                        "duration": "PT2.884S",
                        "startTime": "2024-01-11T06:55:53.545Z",
                        "endTime": "2024-01-11T06:55:56.429Z",
                        "name": "spring.beans.instantiate",
                        "tags": [
                            {
                                "key": "beanName",
                                "value": "shardingSphereDataSource"
                            }
                        ],
                        "children": []
                    },
                    {
                        "id": 284,
                        "parentId": 283,
                        "duration": "PT0.114S",
                        "startTime": "2024-01-11T06:55:53.431Z",
                        "endTime": "2024-01-11T06:55:53.545Z",
                        "name": "spring.beans.instantiate",
                        "tags": [
                            {
                                "key": "beanName",
                                "value": "com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration"
                            }
                        ],
                        "children": []
                    }
                ]
            }
        ]
    },
    {
        "id": 1,
        "duration": "PT3.416S",
        "startTime": "2024-01-11T06:55:32.790Z",
        "endTime": "2024-01-11T06:55:36.206Z",
        "name": "spring.boot.application.environment-prepared",
        "tags": [],
        "children": []
    },
    {
        "id": 271,
        "parentId": 261,
        "duration": "PT0.473S",
        "startTime": "2024-01-11T06:55:52.746Z",
        "endTime": "2024-01-11T06:55:53.219Z",
        "name": "spring.beans.instantiate",
        "tags": [
            {
                "key": "beanName",
                "value": "redissonClient"
            }
        ],
        "children": []
    },
    {
        "id": 263,
        "parentId": 261,
        "duration": "PT0.45S",
        "startTime": "2024-01-11T06:55:52.284Z",
        "endTime": "2024-01-11T06:55:52.734Z",
        "name": "spring.beans.instantiate",
        "tags": [
            {
                "key": "beanName",
                "value": "redisConnectionFactory"
            }
        ],
        "children": [
            {
                "id": 266,
                "parentId": 263,
                "duration": "PT0.356S",
                "startTime": "2024-01-11T06:55:52.302Z",
                "endTime": "2024-01-11T06:55:52.658Z",
                "name": "spring.beans.instantiate",
                "tags": [
                    {
                        "key": "beanName",
                        "value": "lettuceClientResources"
                    }
                ],
                "children": []
            }
        ]
    },
    {
        "id": 14,
        "parentId": 9,
        "duration": "PT0.115S",
        "startTime": "2024-01-11T06:55:47.574Z",
        "endTime": "2024-01-11T06:55:47.689Z",
        "name": "spring.data.repository.scanning",
        "tags": [
            {
                "key": "dataModule",
                "value": "MongoDB"
            },
            {
                "key": "basePackages",
                "value": "cn.linkkids.channel.goods"
            },
            {
                "key": "repository.count",
                "value": "0"
            }
        ],
        "children": []
    }
]
```
