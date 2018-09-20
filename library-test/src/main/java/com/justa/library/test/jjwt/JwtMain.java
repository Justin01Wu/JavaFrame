package com.justa.library.test.jjwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;


// comes from https://blog.csdn.net/qq_37636695/article/details/79265711

// please also see https://blog.csdn.net/a975261294/article/details/71937908
public class JwtMain {
	
	public String createJWT(String id, String subject, long ttlMillis) throws Exception {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256; //指定签名的时候使用的签名算法，也就是header那部分，jjwt已经将这部分内容封装好了。
        long nowMillis = System.currentTimeMillis();//生成JWT的时间
        Date now = new Date(nowMillis);
        
        Map<String,Object> claims = new HashMap<String,Object>();
        //创建payload的私有声明（根据特定的业务需要添加，如果要拿这个做验证，一般是需要和jwt的接收方提前沟通好验证方式的）
        claims.put("uid", "DSSFAWDWADAS...");
        claims.put("user_name", "admin");
        claims.put("nick_name","DASDA121");
        claims.put("justin","I can add any fields into JWT");
        SecretKey key = generalKey();//生成签名的时候使用的秘钥secret,这个方法本地封装了的，一般可以从本地配置文件中读取，切记这个秘钥不能外露哦。它就是你服务端的私钥，在任何场景都不应该流露出去。一旦客户端得知这个secret, 那就意味着客户端是可以自我签发jwt了。
        
        SecretKey key2 = MacProvider.generateKey(); //这里是加密解密的key。


        
        //下面就是在为payload添加各种标准声明和私有声明了
        JwtBuilder builder = Jwts.builder() //这里其实就是new一个JwtBuilder，设置jwt的body
                .setClaims(claims)          //如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                .setId(id)                  //设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
                .setIssuedAt(now)           //iat: jwt的签发时间
                .setSubject(subject)        //sub(Subject)：代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串，可以存放什么userid，roldid之类的，作为什么用户的唯一标志。
                .signWith(signatureAlgorithm, key);//设置签名使用的签名算法和签名使用的秘钥
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);     //设置过期时间
        }
        return builder.compact();           //就开始压缩为xxxxxxxxxxxxxx.xxxxxxxxxxxxxxx.xxxxxxxxxxxxx这样的jwt
        //打印了一哈哈确实是下面的这个样子
        //eyJhbGciOiJIUzI1NiJ9.eyJ1aWQiOiJEU1NGQVdEV0FEQVMuLi4iLCJzdWIiOiIiLCJ1c2VyX25hbWUiOiJhZG1pbiIsIm5pY2tfbmFtZSI6IkRBU0RBMTIxIiwiZXhwIjoxNTE3ODI4MDE4LCJpYXQiOjE1MTc4Mjc5NTgsImp0aSI6Imp3dCJ9.xjIvBbdPbEMBMurmwW6IzBkS3MPwicbqQa2Y5hjHSyo
    }
	
	public Claims parseJWT(String jwt) throws Exception{
        SecretKey key = generalKey();  //签名秘钥，和生成的签名的秘钥一模一样
        Claims claims = Jwts.parser()  //得到DefaultJwtParser
           .setSigningKey(key)         //设置签名的秘钥
           .parseClaimsJws(jwt).getBody();//设置需要解析的jwt
        return claims;
    }
	
	public SecretKey generalKey(){
        byte[] stringKey = JWTSetting.JWT_SECRET.getBytes();  
        // JWT_SECRET will be on server setting on prod, better to add salt to avoid both developer and admin to easily get real key         
        SecretKey key = new SecretKeySpec(stringKey, 0, stringKey.length, "AES");// 根据给定的字节数组使用AES加密算法构造一个密钥，使用 encodedKey中的始于且包含 0 到前 leng 个字节这是当然是所有。（后面的文章中马上回推出讲解Java加密和解密的一些算法）
        return key;
    }
	
	public void printJwt(Claims c){
        System.out.println("  id: " + c.getId()); //jwt Id
        System.out.println("  IssuedAt: " + c.getIssuedAt());  //Mon Feb 05 20:50:49 CST 2018
        System.out.println("  subject: "+ c.getSubject());  //{id:100,name:justin.wu}
        System.out.println("  issuer: " + c.getIssuer());//null
        System.out.println("  uid: " + c.get("uid", String.class));//DSSFAWDWADAS...
        System.out.println("  justin: " + c.get("justin", String.class));
        
		
	}
	
	public static void main(String[] args) throws Exception {
		JwtMain util=   new JwtMain();
        String ab=util.createJWT("2409347239042", "{id:100,name:justin wu}", 60000000);
        System.out.println(ab);
        
        Claims c=util.parseJWT(ab); 
        util.printJwt(c);
        
        String jwt="eyJhbGciOiJIUzI1NiJ9.eyJ1aWQiOiJEU1NGQVdEV0FEQVMuLi4iLCJzdWIiOiJ7aWQ6MTAwLG5hbWU6anVzdGluIHd1fSIsInVzZXJfbmFtZSI6ImFkbWluIiwibmlja19uYW1lIjoiREFTREExMjEiLCJleHAiOjE1Mzc1MTYzOTIsImlhdCI6MTUzNzQ1NjM5MiwianRpIjoiMjQwOTM0NzIzOTA0MiJ9.7xied4UllNhiEjsjGqcOy4P51Zogl7IS4S6Co7jZf1w";
        c=util.parseJWT(jwt);//注意：如果jwt已经过期了，这里会抛出jwt过期异常。
        util.printJwt(c);
    }
}
