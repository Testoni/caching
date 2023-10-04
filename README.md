# Redis caching

<blockquote style="border-left: 5px solid #ff0000; padding-left: 10px;">
  docker run --name my-redis -p 6379:6379 -d redis (running container)
  
  docker exec -it my-redis sh (execute redis)

  redis-cli (client)

  keys * (stored keys)

  get products::1
</blockquote>
