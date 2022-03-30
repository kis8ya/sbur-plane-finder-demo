# Takeaways
## Running rabbitmq
```shell
> docker pull rabbitmq:3.9-management
> docker run --rm -it --hostname sbur-rabbit -p 15672:15672 -p 5672:5672 rabbitmq:3.9-management
```

## Checking messages
http://localhost:15672/#/connections

http://localhost:15672/#/queues
