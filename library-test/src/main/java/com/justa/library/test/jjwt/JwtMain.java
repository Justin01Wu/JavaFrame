package com.justa.library.test.jjwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


// comes from https://blog.csdn.net/qq_37636695/article/details/79265711

// please also see https://blog.csdn.net/a975261294/article/details/71937908
public class JwtMain {
	
	public String createJWT(String id, String subject, long ttlMillis) throws Exception {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256; // 指定签名的时候使用的签名算法，也就是header那部分，jjwt已经将这部分内容封装好了。
        long nowMillis = System.currentTimeMillis();//生成JWT的时间
        Date now = new Date(nowMillis);
        
        Map<String,Object> claims = new HashMap<String,Object>();
        //创建payload的私有声明（根据特定的业务需要添加，如果要拿这个做验证，一般是需要和jwt的接收方提前沟通好验证方式的）
        //claims.put("uid", "DSSFAWDWADAS...");
        //claims.put("user_name", "admin");
        //claims.put("nick_name","DASDA121");
        claims.put("justin","I can add any fields into JWT");
        SecretKey key = JWTSetting.SecretKey;
        //生成签名的时候使用的秘钥secret,这个方法本地封装了的，一般可以从本地配置文件中读取，切记这个秘钥不能外露哦。它就是你服务端的私钥，在任何场景都不应该流露出去。
        //一旦客户端得知这个secret, 那就意味着客户端是可以自我签发jwt了。
        
        String tokenId = UUID.randomUUID().toString();
        //下面就是在为payload添加各种标准声明和私有声明了
        JwtBuilder builder = Jwts.builder() // 这里其实就是new一个JwtBuilder，设置jwt的body
                .setClaims(claims)          // 如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                .setId(tokenId)             // 设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
                .setIssuedAt(now)           // iat: jwt的签发时间
                .setIssuer("Jersey2")           //iss: who issue this token
                .setSubject(subject)        //sub(Subject)：代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串，可以存放什么userid，roldid之类的，作为什么用户的唯一标志。
                .signWith(signatureAlgorithm, key);//设置签名使用的签名算法和签名使用的秘钥
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);     // 设置过期时间
        }
        return builder.compact();           // 就开始压缩为xxxxxxxxxxxxxx.xxxxxxxxxxxxxxx.xxxxxxxxxxxxx这样的jwt
        //打印了一哈哈确实是下面的这个样子
        //eyJhbGciOiJIUzI1NiJ9.eyJ1aWQiOiJEU1NGQVdEV0FEQVMuLi4iLCJzdWIiOiIiLCJ1c2VyX25hbWUiOiJhZG1pbiIsIm5pY2tfbmFtZSI6IkRBU0RBMTIxIiwiZXhwIjoxNTE3ODI4MDE4LCJpYXQiOjE1MTc4Mjc5NTgsImp0aSI6Imp3dCJ9.xjIvBbdPbEMBMurmwW6IzBkS3MPwicbqQa2Y5hjHSyo
    }
	
	public Claims parseJWT(String jwt) throws Exception{
        SecretKey key = JWTSetting.SecretKey;  // 签名秘钥，和生成的签名的秘钥一模一样
        Claims claims = Jwts.parser()  // 得到DefaultJwtParser
           .setSigningKey(key)         // 设置签名的秘钥
           .parseClaimsJws(jwt).getBody();//设置需要解析的jwt
        return claims;
    }
	
	public void printJwt(Claims c){
        System.out.println("  id: " + c.getId()); //jwt Id
        System.out.println("  IssuedAt: " + c.getIssuedAt());  //Mon Feb 05 20:50:49 CST 2018
        System.out.println("  expiredAt: " + c.getExpiration());  //Mon Feb 05 20:50:49 CST 2018
        System.out.println("  subject: "+ c.getSubject());  //{id:100,name:justin.wu}
        System.out.println("  issuer: " + c.getIssuer());//null
        //System.out.println("  uid: " + c.get("uid", String.class));//DSSFAWDWADAS...
        System.out.println("  justin: " + c.get("justin", String.class));        
		
	}
	
	public static void main(String[] args) throws Exception {
		
		//long ttlMillis = 3600000l;  // one hour
		long ttlMillis = 86400000l;   // one day
		//long ttlMillis = 31536000000l;  // one year
		
		JwtMain util=   new JwtMain();
        String ab=util.createJWT("2409347239042", "{id:100,name:justin wu}", ttlMillis);
        System.out.println(ab);
        
        Claims c=util.parseJWT(ab); 
        util.printJwt(c);
        
        System.out.println();
        
        String jwt="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ7aWQ6MTAwLG5hbWU6anVzdGluIHd1fSIsImlzcyI6IkplcnNleTIiLCJqdXN0aW4iOiJJIGNhbiBhZGQgYW55IGZpZWxkcyBpbnRvIEpXVCIsImV4cCI6MTU5Nzk4Mzk2NywiaWF0IjoxNTM3OTgzOTY3LCJqdGkiOiJmMTA2MGMwZC01YjU4LTRhZTYtODg4Yy1hZjI4NmJkNjBhOGUifQ.hI--i6x1817S9kui3Fh8IBNzZ3FlI6t0uAPX9meX0C0";
        c=util.parseJWT(jwt);//注意：如果jwt已经过期了，这里会抛出jwt过期异常。
        util.printJwt(c);
        
//  RAS token sample
//        jwt = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICI4VVhKcFNZcThJNF96aUhYX0xxVnUzOTNnQ3NYUUZqLUp3MDNvN3VYako0In0.eyJqdGkiOiJmYjllOWY5Yi0wM2Y2LTRkNTItOGUxYS0zOGI5MGJlYjI3NDMiLCJleHAiOjE1Nzg1MjA5NTcsIm5iZiI6MCwiaWF0IjoxNTc4NTA2NTU3LCJpc3MiOiJodHRwczovL2lkcDAyLnZhbGlkdXNob2xkaW5ncy5jb206ODQ0My9hdXRoL3JlYWxtcy92Y2Fwcy10ZXN0IiwiYXVkIjoidmNhcHMtcWEtODA4NiIsInN1YiI6ImI3MTdhMDEzLTU0MDYtNDZjZC04YmU2LTkxYmI1YTU3Y2U5MyIsInR5cCI6IkJlYXJlciIsImF6cCI6InZjYXBzLXFhLTgwODYiLCJhdXRoX3RpbWUiOjE1Nzg0OTE5NzgsInNlc3Npb25fc3RhdGUiOiI3MDg1NzA3Ni1mODAwLTRhMjItYTBkNi05OTQ1Yjg5YmVhNDAiLCJhY3IiOiIwIiwiYWxsb3dlZC1vcmlnaW5zIjpbIioiXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbInZjYXBzLWFkbWluIiwib2ZmbGluZV9hY2Nlc3MiLCJ1bWFfYXV0aG9yaXphdGlvbiJdfSwicmVzb3VyY2VfYWNjZXNzIjp7ImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sImF1dGhvcml6YXRpb24iOnsicGVybWlzc2lvbnMiOlt7InJzaWQiOiI4YmQwZWIwYi1lZWNkLTRiYTgtOGQ1Yy01YzVkNmIwYmJkZmYiLCJyc25hbWUiOiJEZWZhdWx0IFJlc291cmNlIn1dfSwic2NvcGUiOiJvcGVuaWQgcHJvZmlsZSBlbWFpbCIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwicHJlZmVycmVkX3VzZXJuYW1lIjoianVzdGluLnd1IiwiZW1haWwiOiJqdXN0aW4ud3VAZ2xvYmFsLmxvY2FsIn0.j9leDHohvkQL0JDoVEO0zU_MMtFAqPlUcTfnq6XfXIHu7YlcbiHIpclHqEFHm08J0VAw2t58hPhmqJZ9Q4pWpgKUyM-okXlK5mw4Phez25Yamo0Ztko9agACvVCbhd6c7fBW5ysKbLrgMDOtX_2QoVg0MGF9eRATG1KUpD0fHeukyM50-zLz1RQXRAK77WsQsyLf4GeBDg_evD71D1LdmbA5RYIEBPc7Kng_Xh0JpEnVs30a6x-8pPIEgp3zFtfsl-ZAB_igJoKSoB7RLp2dDi1rifU0rGwQVn94PbkYiRXoaKvJJlthuJ-KHLszkMxiMM5D-YLJKGiMgXdwSiXh5Q";
//        c=util.parseJWT(jwt);
//        util.printJwt(c);

    }
}
