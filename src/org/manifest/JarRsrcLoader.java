/*     */ package org.manifest;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.net.URL;
/*     */ import java.net.URLClassLoader;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Enumeration;
/*     */ import java.util.List;
/*     */ import java.util.jar.Attributes;
/*     */ import java.util.jar.Manifest;
/*     */ 
/*     */ public class JarRsrcLoader {
/*     */   private static class ManifestInfo {
/*     */     String rsrcMainClass;
/*     */     
/*     */     String[] rsrcClassPath;
/*     */     
/*     */     private ManifestInfo() {}
/*     */   }
/*     */   
/*     */   public static void main(String[] args) throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException, IOException {
/*  46 */     ManifestInfo mi = getManifestInfo();
/*  47 */     ClassLoader cl = Thread.currentThread().getContextClassLoader();
/*  48 */     URL.setURLStreamHandlerFactory(new RsrcURLStreamHandlerFactory(cl));
/*  49 */     URL[] rsrcUrls = new URL[mi.rsrcClassPath.length];
/*  50 */     for (int i = 0; i < mi.rsrcClassPath.length; i++) {
/*  51 */       String rsrcPath = mi.rsrcClassPath[i];
/*  52 */       if (rsrcPath.endsWith("/")) {
/*  53 */         rsrcUrls[i] = new URL("rsrc:" + rsrcPath);
/*     */       } else {
/*  55 */         rsrcUrls[i] = new URL("jar:rsrc:" + rsrcPath + "!/");
/*     */       } 
/*     */     } 
/*  57 */     ClassLoader jceClassLoader = new URLClassLoader(rsrcUrls, getParentClassLoader());
/*  58 */     Thread.currentThread().setContextClassLoader(jceClassLoader);
/*  59 */     Class<?> c = Class.forName(mi.rsrcMainClass, true, jceClassLoader);
/*  60 */     Method main = c.getMethod("main", new Class[] { args.getClass() });
/*  61 */     main.invoke(null, new Object[] { args });
/*     */   }
/*     */   
/*     */   private static ClassLoader getParentClassLoader() throws InvocationTargetException, IllegalAccessException {
/*     */     try {
/*  71 */       Method platformClassLoader = ClassLoader.class.getMethod("getPlatformClassLoader", null);
/*  72 */       return (ClassLoader)platformClassLoader.invoke(null, null);
/*  73 */     } catch (NoSuchMethodException noSuchMethodException) {
/*  75 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static ManifestInfo getManifestInfo() throws IOException {
/*  81 */     Enumeration<URL> resEnum = Thread.currentThread().getContextClassLoader().getResources("META-INF/MANIFEST.MF");
/*  82 */     while (resEnum.hasMoreElements()) {
/*     */       try {
/*  84 */         URL url = resEnum.nextElement();
/*  85 */         InputStream is = url.openStream();
/*  86 */         if (is != null) {
/*  87 */           ManifestInfo result = new ManifestInfo();
/*  88 */           Manifest manifest = new Manifest(is);
/*  89 */           Attributes mainAttribs = manifest.getMainAttributes();
/*  90 */           result.rsrcMainClass = mainAttribs.getValue("Rsrc-Main-Class");
/*  91 */           String rsrcCP = mainAttribs.getValue("Rsrc-Class-Path");
/*  92 */           if (rsrcCP == null)
/*  93 */             rsrcCP = ""; 
/*  94 */           result.rsrcClassPath = splitSpaces(rsrcCP);
/*  95 */           if (result.rsrcMainClass != null && !result.rsrcMainClass.trim().isEmpty())
/*  96 */             return result; 
/*     */         } 
/*  99 */       } catch (Exception exception) {}
/*     */     } 
/* 103 */     System.err.println("Missing attributes for JarRsrcLoader in Manifest (Rsrc-Main-Class, Rsrc-Class-Path)");
/* 104 */     return null;
/*     */   }
/*     */   
/*     */   @SuppressWarnings({ "rawtypes", "unchecked" })
private static String[] splitSpaces(String line) {
/* 115 */     if (line == null)
/* 116 */       return null; 
/* 117 */     List<String> result = new ArrayList();
/* 118 */     int firstPos = 0;
/* 119 */     while (firstPos < line.length()) {
/* 120 */       int lastPos = line.indexOf(' ', firstPos);
/* 121 */       if (lastPos == -1)
/* 122 */         lastPos = line.length(); 
/* 123 */       if (lastPos > firstPos)
/* 124 */         result.add(line.substring(firstPos, lastPos)); 
/* 126 */       firstPos = lastPos + 1;
/*     */     } 
/* 128 */     return result.<String>toArray(new String[result.size()]);
/*     */   }
/*     */ }

