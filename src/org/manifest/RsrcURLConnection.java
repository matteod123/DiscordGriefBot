/*    */ package org.manifest;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.net.MalformedURLException;
/*    */ import java.net.URL;
/*    */ import java.net.URLConnection;
/*    */ import java.net.URLDecoder;
/*    */ 
/*    */ public class RsrcURLConnection extends URLConnection {
/*    */   private ClassLoader classLoader;
/*    */   
/*    */   public RsrcURLConnection(URL url, ClassLoader classLoader) {
/* 38 */     super(url);
/* 39 */     this.classLoader = classLoader;
/*    */   }
/*    */   
/*    */   public void connect() throws IOException {}
/*    */   
/*    */   public InputStream getInputStream() throws IOException {
/* 48 */     String file = URLDecoder.decode(this.url.getFile(), "UTF-8");
/* 49 */     InputStream result = this.classLoader.getResourceAsStream(file);
/* 50 */     if (result == null)
/* 51 */       throw new MalformedURLException("Could not open InputStream for URL '" + this.url + "'"); 
/* 53 */     return result;
/*    */   }
/*    */ }

