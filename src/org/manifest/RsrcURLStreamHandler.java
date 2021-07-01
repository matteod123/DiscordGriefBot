/*    */ package org.manifest;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.net.URL;
/*    */ import java.net.URLConnection;
/*    */ import java.net.URLStreamHandler;
/*    */ 
/*    */ public class RsrcURLStreamHandler extends URLStreamHandler {
/*    */   private ClassLoader classLoader;
/*    */   
/*    */   public RsrcURLStreamHandler(ClassLoader classLoader) {
/* 37 */     this.classLoader = classLoader;
/*    */   }
/*    */   
/*    */   protected URLConnection openConnection(URL u) throws IOException {
/* 42 */     return new RsrcURLConnection(u, this.classLoader);
/*    */   }
/*    */   
/*    */   protected void parseURL(URL url, String spec, int start, int limit) {
/*    */     String file;
/* 48 */     if (spec.startsWith("rsrc:")) {
/* 49 */       file = spec.substring(5);
/* 50 */     } else if (url.getFile().equals("./")) {
/* 51 */       file = spec;
/* 52 */     } else if (url.getFile().endsWith("/")) {
/* 53 */       file = String.valueOf(url.getFile()) + spec;
/* 54 */     } else if ("#runtime".equals(spec)) {
/* 55 */       file = url.getFile();
/*    */     } else {
/* 57 */       file = spec;
/*    */     } 
/* 58 */     setURL(url, "rsrc", "", -1, null, null, file, null, null);
/*    */   }
/*    */ }

