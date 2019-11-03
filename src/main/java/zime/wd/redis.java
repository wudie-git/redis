package zime.wd;

import com.alibaba.fastjson.JSON;
import redis.clients.jedis.Jedis;

import java.awt.*;

public class redis {
    public static void main(String[] args) {
        //连接本地的 Redis 服务
        Jedis jedis = new Jedis("localhost");
        Article article=new Article();
        article.setTitle("窝窝头");
        article.setAuthor("一块钱");
        article.setContent("四个");
        article.setTime("嘿嘿");
        Long postId=save(article,jedis);
        System.out.println("success");
        article =get(jedis, postId);
        System.out.println(article);
        System.out.println("获取成功");

    }
  public static Long save(Article article,Jedis jedis){
      long post=jedis.incr("post");
      String posts= JSON.toJSONString(article);
      jedis.set("post"+post+"date:",posts);
      return post;

  }
    public static Article get(Jedis jedis, long postId){
        String postid=jedis.get("post:" + postId + ":data");
        Article article1 =(Article)JSON.parseObject(postid, Article.class);
        return article1;

    }
    public static Long del(Jedis jedis,Long id){
        jedis.del("post:"+id+":data");
        return id;
    }
    public static Long upd(Article article,Jedis jedis,Long id){
        Long postId2=id;
        String stu= JSON.toJSONString(article);
        jedis.set("post:"+postId2+":data",stu);
        return postId2;
    }



}
