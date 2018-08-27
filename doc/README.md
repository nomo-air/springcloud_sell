
# 此项目源于www.imooc.com，自己按照视频写的，并非自己原创

docker run -d --hostname my-rabbit -p 5672:5672 -p 15672:15672 rabbitmq:3.7.3-management

docker run -d -p 6379:6379 redis:4.0.8