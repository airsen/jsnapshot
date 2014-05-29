package info.airsen.tmp;

import java.io.*;

/**
 * @author 主公 (jason61719@gmail.com)
 * @since 14-5-28 下午8:16
 */
public class Main {

	public static void main(String[] args) throws IOException {

		String fileName = "string.txt";
		BufferedReader fromFile = null;

		FileReader fr = new FileReader(fileName);
		fromFile = new BufferedReader(fr);
		System.out.println("The file " + fileName + " contains the following lines:");
		String line;
		StringBuilder totalContent = new StringBuilder();
		while ((line = fromFile.readLine()) != null) {
			System.out.println(line);
			totalContent.append(line);
		}
		fromFile.close();
		fr.close();

		ProtoBufferPractice.msgInfo.Builder builder = ProtoBufferPractice.msgInfo.newBuilder();
		builder.setId(123455678);
		builder.setDes("线程上下文类加载器（context class loader）是从 JDK 1.2 开始引入的。类 java.lang.Thread中的方法 getContextClassLoader()和 setContextClassLoader(ClassLoader cl)用来获取和设置线程的上下文类加载器。如果没有通过 setContextClassLoader(ClassLoader cl)方法进行设置的话，线程将继承其父线程的上下文类加载器。Java 应用运行的初始线程的上下文类加载器是系统类加载器。在线程中运行的代码可以通过此类加载器来加载类和资源。前面提到的类加载器的代理模式并不能解决 Java 应用开发中会遇到的类加载器的全部问题。Java 提供了很多服务提供者接口（Service Provider Interface，SPI），允许第三方为这些接口提供实现。常见的 SPI 有 JDBC、JCE、JNDI、JAXP 和 JBI 等。这些 SPI 的接口由 Java 核心库来提供，如 JAXP 的 SPI 接口定义包含在 javax.xml.parsers包中。这些 SPI 的实现代码很可能是作为 Java 应用所依赖的 jar 包被包含进来，可以通过类路径（CLASSPATH）来找到，如实现了 JAXP SPI 的 Apache Xerces所包含的 jar 包。SPI 接口中的代码经常需要加载具体的实现类。如 JAXP 中的 javax.xml.parsers.DocumentBuilderFactory类中的 newInstance()方法用来生成一个新的 DocumentBuilderFactory的实例。这里的实例的真正的类是继承自 javax.xml.parsers.DocumentBuilderFactory，由 SPI 的实现所提供的。如在 Apache Xerces 中，实现的类是 org.apache.xerces.jaxp.DocumentBuilderFactoryImpl。而问题在于，SPI 的接口是 Java 核心库的一部分，是由引导类加载器来加载的；SPI 实现的 Java 类一般是由系统类加载器来加载的。引导类加载器是无法找到 SPI 的实现类的，因为它只加载 Java 的核心库。它也不能代理给系统类加载器，因为它是系统类加载器的祖先类加载器。也就是说，类加载器的代理模式无法解决这个问题。");
		ProtoBufferPractice.msgInfo info = builder.build();

		byte[] result = info.toByteArray();

		FileOutputStream out = new FileOutputStream(new File("msg.cpd"));
		out.write(result);
		out.flush();
		out.close();

	}

}
