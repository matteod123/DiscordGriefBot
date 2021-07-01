/*    */ package org.manifest;
/*    */ 
/*    */ import java.net.URLStreamHandler;
/*    */ import java.net.URLStreamHandlerFactory;
/*    */ 
/*    */ public class RsrcURLStreamHandlerFactory implements URLStreamHandlerFactory {
/*    */   private ClassLoader classLoader;
/*    */   
/*    */   private URLStreamHandlerFactory chainFac;
/*    */   
/*    */   public RsrcURLStreamHandlerFactory(ClassLoader cl) {
/* 34 */     this.classLoader = cl;
/*    */   }
/*    */   
/*    */   public URLStreamHandler createURLStreamHandler(String protocol) {
/* 39 */     if ("rsrc".equals(protocol))
/* 40 */       return new RsrcURLStreamHandler(this.classLoader); 
/* 41 */     if (this.chainFac != null)
/* 42 */       return this.chainFac.createURLStreamHandler(protocol); 
/* 43 */     return null;
/*    */   }
/*    */   
/*    */   public void setURLStreamHandlerFactory(URLStreamHandlerFactory fac) {
/* 55 */     this.chainFac = fac;
/*    */   }
/*    */ }
