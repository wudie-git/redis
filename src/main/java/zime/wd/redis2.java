package zime.wd;

import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;

public class redis2 {
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
        Article article1 =get(jedis, postId);
        System.out.println(article1);
        System.out.println("获取成功");
    }
    public static long  save(Article article, Jedis jedis){
        Long postId =jedis.incr("post");
        Map<String,String> blog=new HashMap<String, String>();
        blog.put("title",article.getTitle());
        blog.put("content",article.getContent());
        blog.put("author",article.getAuthor());
        blog.put("time",article.getTime());
        jedis.hmset("post:" +postId+":blog" ,blog);
        return postId;
    }
    public static Article get(Jedis jedis, long postId){
        Map<String,String>myBlog =jedis.hgetAll("post"+postId+":data");
        Article article=new Article();
        article.setTitle(myBlog.get("title"));
        article.setContent(myBlog.get("content"));
        article.setAuthor(myBlog.get("author"));
        article.setTime(myBlog.get("time"));
        return article;
    }
    public static Long del(Jedis jedis,Long id){
        jedis.del("post:"+id+":data");
        return id;
    }
    static Long upd(Article article,Jedis jedis,Long id){
        Long postId2=id;
        Map<String,String> blog =new HashMap<String, String>();
        blog.put("title",article.getTitle());
        blog.put("author",article.getAuthor());
        blog.put("content",article.getContent());
        blog.put("time",article.getTime());
        jedis.hmset("post:"+postId2+":data",blog);
        return postId2;
    }

}
