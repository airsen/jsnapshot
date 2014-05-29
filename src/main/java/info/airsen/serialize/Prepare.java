package info.airsen.serialize;

import info.airsen.serialize.json.GsonSerializer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author 主公 (jason61719@gmail.com)
 * @since 14-5-28 下午10:01
 */
public class Prepare {

	public static Integer STRING = 1;
	public static Integer INTEGER = 2;
	public static Integer FLOAT = 3;

	public static void main(String[] args) throws IOException {
		int recordCount = 1000000;
		int type = FLOAT;

		String json = GsonSerializer.toJson(buildTest(type, recordCount));

		FileOutputStream out = new FileOutputStream(new File("src/main/java/info/airsen/serialize/json/list_" + type + "_" + recordCount + ".json"));
		out.write(json.getBytes());
		out.flush();
		out.close();
	}

	private static String getRandomeString(Integer size) {
		String tpl = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		Random random = new Random();
		StringBuilder builder = new StringBuilder();
		while (builder.length() < size) {
			builder.append(tpl.charAt(random.nextInt(tpl.length())));
		}
		return builder.toString();
	}

	private static List<User> buildTest(int type, int recordCount) {
		List<User> userList = new ArrayList<User>();
		Random random = new Random();
		if (type == STRING) {
			for (int i = 0; i < recordCount; i++) {
				User user = new User();
				user.setId(random.nextInt());
				user.setMale(random.nextBoolean());
				user.setName(getRandomeString(30));
				user.setHomepage("http://" + getRandomeString(50));
				user.setNickname(getRandomeString(30));
//			user.setDesc(getRandomeString(1000));
				user.setDesc("dW登录(或注册)中文IBMdeveloperWorks®技术主题软件下载社区技术讲座搜索developerWorks打印本页面用电子邮件发送本页面新浪微博人人网腾讯微博搜狐微博网易微博DiggFacebookTwitterDeliciousLinkedIndeveloperWorks中国技术主题Javatechnology文档库深入探讨Java类加载器类加载器（classloader）是Java™中的一个很重要的概念。类加载器负责加载Java类的字节代码到Java虚拟机中。本文首先详细介绍了Java类加载器的基本概念，包括代理模式、加载类的具体过程和线程上下文类加载器等，接着介绍如何开发自己的类加载器，最后介绍了类加载器在Web容器和OSGi™中的应用。29评论：成富,软件工程师,IBM中国软件开发中心2010年3月01日+内容类加载器是Java语言的一个创新，也是Java语言流行的重要原因之一。它使得Java类可以被动态加载到Java虚拟机中并执行。类加载器从JDK1.0就出现了，最初是为了满足JavaApplet的需要而开发出来的。JavaApplet需要从远程下载Java类文件到浏览器中并执行。现在类加载器在Web容器和OSGi中得到了广泛的使用。一般来说，Java应用的开发人员不需要直接同类加载器进行交互。Java虚拟机默认的行为就已经足够满足大多数情况的需求了。不过如果遇到了需要与类加载器进行交互的情况，而对类加载器的机制又不是很了解的话，就很容易花大量的时间去调试ClassNotFoundException和NoClassDefFoundError等异常。本文将详细介绍Java的类加载器，帮助读者深刻理解Java语言中的这个重要概念。下面首先介绍一些相关的基本概念。类加载器基本概念顾名思义，类加载器（classloader）用来加载Java类到Java虚拟机中。一般来说，Java虚拟机使用Java类的方式如下：Java源程序（.java文件）在经过Java编译器编译之后就被转换成Java字节代码（.class文件）。类加载器负责读取Java字节代码，并转换成java.lang.Class类的一个实例。每个这样的实例用来表示一个Java类。通过此实例的newInstance()方法就可以创建出该类的一个对象。实际的情况可能更加复杂，比如Java字节代码可能是通过工具动态生成的，也可能是通过网络下载的。基本上所有的类加载器都是java.lang.ClassLoader类的一个实例。下面详细介绍这个Java类。java.lang.ClassLoader类介绍java.lang.ClassLoader类的基本职责就是根据一个指定的类的名称，找到或者生成其对应的字节代码，然后从这些字节代码中定义出一个Java类，即java.lang.Class类的一个实例。除此之外，ClassLoader还负责加载Java应用所需的资源，如图像文件和配置文件等。不过本文只讨论其加载类的功能。为了完成加载类的这个职责，ClassLoader提供了一系列的方法，比较重要的方法如表1所示。关于这些方法的细节会在下面进行介绍。表1.ClassLoader中与加载类相关的方法方法说明getParent()返回该类加载器的父类加载器。loadClass(Stringname)加载名称为name的类，返回的结果是java.lang.Class类的实例。findClass(Stringname)查找名称为name的类，返回的结果是java.lang.Class类的实例。findLoadedClass(Stringname)查找名称为name的已经被加载过的类，返回的结果是java.lang.Class类的实例。defineClass(Stringname,byte[]b,intoff,intlen)把字节数组b中的内容转换成Java类，返回的结果是java.lang.Class类的实例。这个方法被声明为final的。resolveClass(Class<?>c)链接指定的Java类。对于表1中给出的方法，表示类名称的name参数的值是类的二进制名称。需要注意的是内部类的表示，如com.example.Sample$1和com.example.Sample$Inner等表示方式。这些方法会在下面介绍类加载器的工作机制时，做进一步的说明。下面介绍类加载器的树状组织结构。类加载器的树状组织结构Java中的类加载器大致可以分成两类，一类是系统提供的，另外一类则是由Java应用开发人员编写的。系统提供的类加载器主要有下面三个：引导类加载器（bootstrapclassloader）：它用来加载Java的核心库，是用原生代码来实现的，并不继承自java.lang.ClassLoader。扩展类加载器（extensionsclassloader）：它用来加载Java的扩展库。Java虚拟机的实现会提供一个扩展库目录。该类加载器在此目录里面查找并加载Java类。系统类加载器（systemclassloader）：它根据Java应用的类路径（CLASSPATH）来加载Java类。一般来说，Java应用的类都是由它来完成加载的。可以通过ClassLoader.getSystemClassLoader()来获取它。除了系统提供的类加载器以外，开发人员可以通过继承java.lang.ClassLoader类的方式实现自己的类加载器，以满足一些特殊的需求。除了引导类加载器之外，所有的类加载器都有一个父类加载器。通过表1中给出的getParent()方法可以得到。对于系统提供的类加载器来说，系统类加载器的父类加载器是扩展类加载器，而扩展类加载器的父类加载器是引导类加载器；对于开发人员编写的类加载器来说，其父类加载器是加载此类加载器Java类的类加载器。因为类加载器Java类如同其它的Java类一样，也是要由类加载器来加载的。一般来说，开发人员编写的类加载器的父类加载器是系统类加载器。类加载器通过这种方式组织起来，形成树状结构。树的根节点就是引导类加载器。图1中给出了一个典型的类加载器树状组织结构示意图，其中的箭头指向的是父类加载器。图1.类加载器树状组织结构示意图类加载器树状组织结构示意图代码清单1演示了类加载器的树状组织结构。清单1.演示类加载器的树状组织结构publicclassClassLoaderTree{publicstaticvoidmain(String[]args){ClassLoaderloader=ClassLoaderTree.class.getClassLoader();while(loader!=null){System.out.println(loader.toString());loader=loader.getParent();}}}每个Java类都维护着一个指向定义它的类加载器的引用，通过getClassLoader()方法就可以获取到此引用。代码清单1中通过递归调用getParent()方法来输出全部的父类加载器。代码清单1的运行结果如代码清单2所示。清单2.演示类加载器的树状组织结构的运行结果sun.misc.Launcher$AppClassLoader@9304b1sun.misc.Launcher$ExtClassLoader@190d11如代码清单2所示，第一个输出的是ClassLoaderTree类的类加载器，即系统类加载器。它是sun.misc.Launcher$AppClassLoader类的实例；第二个输出的是扩展类加载器，是sun.misc.Launcher$ExtClassLoader类的实例。需要注意的是这里并没有输出引导类加载器，这是由于有些JDK的实现对于父类加载器是引导类加载器的情况，getParent()方法返回null。在了解了类加载器的树状组织结构之后，下面介绍类加载器的代理模式。类加载器的代理模式类加载器在尝试自己去查找某个类的字节代码并定义它时，会先代理给其父类加载器，由父类加载器先去尝试加载这个类，依次类推。在介绍代理模式背后的动机之前，首先需要说明一下Java虚拟机是如何判定两个Java类是相同的。Java虚拟机不仅要看类的全名是否相同，还要看加载此类的类加载器是否一样。只有两者都相同的情况，才认为两个类是相同的。即便是同样的字节代码，被不同的类加载器加载之后所得到的类，也是不同的。比如一个Java类com.example.Sample，编译之后生成了字节代码文件Sample.class。两个不同的类加载器ClassLoaderA和ClassLoaderB分别读取了这个Sample.class文件，并定义出两个java.lang.Class类的实例来表示这个类。这两个实例是不相同的。对于Java虚拟机来说，它们是不同的类。试图对这两个类的对象进行相互赋值，会抛出运行时异常ClassCastException。下面通过示例来具体说明。代码清单3中给出了Java类com.example.Sample。清单3.com.example.Sample类packagecom.example;publicclassSample{privateSampleinstance;publicvoidsetSample(Objectinstance){this.instance=(Sample)instance;}}如代码清单3所示，com.example.Sample类的方法setSample接受一个java.lang.Object类型的参数，并且会把该参数强制转换成com.example.Sample类型。测试Java类是否相同的代码如代码清单4所示。清单4.测试Java类是否相同publicvoidtestClassIdentity(){StringclassDataRootPath=\"C:\\\\workspace\\\\Classloader\\\\classData\";FileSystemClassLoaderfscl1=newFileSystemClassLoader(classDataRootPath);FileSystemClassLoaderfscl2=newFileSystemClassLoader(classDataRootPath);StringclassName=\"com.example.Sample\";try{Class<?>class1=fscl1.loadClass(className);Objectobj1=class1.newInstance();Class<?>class2=fscl2.loadClass(className);Objectobj2=class2.newInstance();MethodsetSampleMethod=class1.getMethod(\"setSample\",java.lang.Object.class);setSampleMethod.invoke(obj1,obj2);}catch(Exceptione){e.printStackTrace();}}代码清单4中使用了类FileSystemClassLoader的两个不同实例来分别加载类com.example.Sample，得到了两个不同的java.lang.Class的实例，接着通过newInstance()方法分别生成了两个类的对象obj1和obj2，最后通过Java的反射API在对象obj1上调用方法setSample，试图把对象obj2赋值给obj1内部的instance对象。代码清单4的运行结果如代码清单5所示。清单5.测试Java类是否相同的运行结果java.lang.reflect.InvocationTargetExceptionatsun.reflect.NativeMethodAccessorImpl.invoke0(NativeMethod)atsun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:39)atsun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:25)atjava.lang.reflect.Method.invoke(Method.java:597)atclassloader.ClassIdentity.testClassIdentity(ClassIdentity.java:26)atclassloader.ClassIdentity.main(ClassIdentity.java:9)Causedby:java.lang.ClassCastException:com.example.Samplecannotbecasttocom.example.Sampleatcom.example.Sample.setSample(Sample.java:7)...6more从代码清单5给出的运行结果可以看到，运行时抛出了java.lang.ClassCastException异常。虽然两个对象obj1和obj2的类的名字相同，但是这两个类是由不同的类加载器实例来加载的，因此不被Java虚拟机认为是相同的。了解了这一点之后，就可以理解代理模式的设计动机了。代理模式是为了保证Java核心库的类型安全。所有Java应用都至少需要引用java.lang.Object类，也就是说在运行的时候，java.lang.Object这个类需要被加载到Java虚拟机中。如果这个加载过程由Java应用自己的类加载器来完成的话，很可能就存在多个版本的java.lang.Object类，而且这些类之间是不兼容的。通过代理模式，对于Java核心库的类的加载工作由引导类加载器来统一完成，保证了Java应用所使用的都是同一个版本的Java核心库的类，是互相兼容的。不同的类加载器为相同名称的类创建了额外的名称空间。相同名称的类可以并存在Java虚拟机中，只需要用不同的类加载器来加载它们即可。不同类加载器加载的类之间是不兼容的，这就相当于在Java虚拟机内部创建了一个个相互隔离的Java类空间。这种技术在许多框架中都被用到，后面会详细介绍。下面具体介绍类加载器加载类的详细过程。加载类的过程在前面介绍类加载器的代理模式的时候，提到过类加载器会首先代理给其它类加载器来尝试加载某个类。这就意味着真正完成类的加载工作的类加载器和启动这个加载过程的类加载器，有可能不是同一个。真正完成类的加载工作是通过调用defineClass来实现的；而启动类的加载过程是通过调用loadClass来实现的。前者称为一个类的定义加载器（definingloader），后者称为初始加载器（initiatingloader）。在Java虚拟机判断两个类是否相同的时候，使用的是类的定义加载器。也就是说，哪个类加载器启动类的加载过程并不重要，重要的是最终定义这个类的加载器。两种类加载器的关联之处在于：一个类的定义加载器是它引用的其它类的初始加载器。如类com.example.Outer引用了类com.example.Inner，则由类com.example.Outer的定义加载器负责启动类com.example.Inner的加载过程。方法loadClass()抛出的是java.lang.ClassNotFoundException异常；方法defineClass()抛出的是java.lang.NoClassDefFoundError异常。类加载器在成功加载某个类之后，会把得到的java.lang.Class类的实例缓存起来。下次再请求加载该类的时候，类加载器会直接使用缓存的类的实例，而不会尝试再次加载。也就是说，对于一个类加载器实例来说，相同全名的类只加载一次，即loadClass方法不会被重复调用。下面讨论另外一种类加载器：线程上下文类加载器。线程上下文类加载器线程上下文类加载器（contextclassloader）是从JDK1.2开始引入的。类java.lang.Thread中的方法getContextClassLoader()和setContextClassLoader(ClassLoadercl)用来获取和设置线程的上下文类加载器。如果没有通过setContextClassLoader(ClassLoadercl)方法进行设置的话，线程将继承其父线程的上下文类加载器。Java应用运行的初始线程的上下文类加载器是系统类加载器。在线程中运行的代码可以通过此类加载器来加载类和资源。前面提到的类加载器的代理模式并不能解决Java应用开发中会遇到的类加载器的全部问题。Java提供了很多服务提供者接口（ServiceProviderInterface，SPI），允许第三方为这些接口提供实现。常见的SPI有JDBC、JCE、JNDI、JAXP和JBI等。这些SPI的接口由Java核心库来提供，如JAXP的SPI接口定义包含在javax.xml.parsers包中。这些SPI的实现代码很可能是作为Java应用所依赖的jar包被包含进来，可以通过类路径（CLASSPATH）来找到，如实现了JAXPSPI的ApacheXerces所包含的jar包。SPI接口中的代码经常需要加载具体的实现类。如JAXP中的javax.xml.parsers.DocumentBuilderFactory类中的newInstance()方法用来生成一个新的DocumentBuilderFactory的实例。这里的实例的真正的类是继承自javax.xml.parsers.DocumentBuilderFactory，由SPI的实现所提供的。如在ApacheXerces中，实现的类是org.apache.xerces.jaxp.DocumentBuilderFactoryImpl。而问题在于，SPI的接口是Java核心库的一部分，是由引导类加载器来加载的；SPI实现的Java类一般是由系统类加载器来加载的。引导类加载器是无法找到SPI的实现类的，因为它只加载Java的核心库。它也不能代理给系统类加载器，因为它是系统类加载器的祖先类加载器。也就是说，类加载器的代理模式无法解决这个问题。线程上下文类加载器正好解决了这个问题。如果不做任何的设置，Java应用的线程的上下文类加载器默认就是系统上下文类加载器。在SPI接口的代码中使用线程上下文类加载器，就可以成功的加载到SPI实现的类。线程上下文类加载器在很多SPI的实现中都会用到。下面介绍另外一种加载类的方法：Class.forName。Class.forNameClass.forName是一个静态方法，同样可以用来加载类。该方法有两种形式：Class.forName(Stringname,booleaninitialize,ClassLoaderloader)和Class.forName(StringclassName)。第一种形式的参数name表示的是类的全名；initialize表示是否初始化类；loader表示加载时使用的类加载器。第二种形式则相当于设置了参数initialize的值为true，loader的值为当前类的类加载器。Class.forName的一个很常见的用法是在加载数据库驱动的时候。如Class.forName(\"org.apache.derby.jdbc.EmbeddedDriver\").newInstance()用来加载ApacheDerby数据库的驱动。在介绍完类加载器相关的基本概念之后，下面介绍如何开发自己的类加载器。回页首开发自己的类加载器虽然在绝大多数情况下，系统默认提供的类加载器实现已经可以满足需求。但是在某些情况下，您还是需要为应用开发出自己的类加载器。比如您的应用通过网络来传输Java类的字节代码，为了保证安全性，这些字节代码经过了加密处理。这个时候您就需要自己的类加载器来从某个网络地址上读取加密后的字节代码，接着进行解密和验证，最后定义出要在Java虚拟机中运行的类来。下面将通过两个具体的实例来说明类加载器的开发。文件系统类加载器第一个类加载器用来加载存储在文件系统上的Java字节代码。完整的实现如代码清单6所示。清单6.文件系统类加载器publicclassFileSystemClassLoaderextendsClassLoader{privateStringrootDir;publicFileSystemClassLoader(StringrootDir){this.rootDir=rootDir;}protectedClass<?>findClass(Stringname)throwsClassNotFoundException{byte[]classData=getClassData(name);if(classData==null){thrownewClassNotFoundException();}else{returndefineClass(name,classData,0,classData.length);}}privatebyte[]getClassData(StringclassName){Stringpath=classNameToPath(className);try{InputStreamins=newFileInputStream(path);ByteArrayOutputStreambaos=newByteArrayOutputStream();intbufferSize=4096;byte[]buffer=newbyte[bufferSize];intbytesNumRead=0;while((bytesNumRead=ins.read(buffer))!=-1){baos.write(buffer,0,bytesNumRead);}returnbaos.toByteArray();}catch(IOExceptione){e.printStackTrace();}returnnull;}privateStringclassNameToPath(StringclassName){returnrootDir+File.separatorChar+className.replace('.',File.separatorChar)+\".class\";}}如代码清单6所示，类FileSystemClassLoader继承自类java.lang.ClassLoader。在表1中列出的java.lang.ClassLoader类的常用方法中，一般来说，自己开发的类加载器只需要覆写findClass(Stringname)方法即可。java.lang.ClassLoader类的方法loadClass()封装了前面提到的代理模式的实现。该方法会首先调用findLoadedClass()方法来检查该类是否已经被加载过；如果没有加载过的话，会调用父类加载器的loadClass()方法来尝试加载该类；如果父类加载器无法加载该类的话，就调用findClass()方法来查找该类。因此，为了保证类加载器都正确实现代理模式，在开发自己的类加载器时，最好不要覆写loadClass()方法，而是覆写findClass()方法。类FileSystemClassLoader的findClass()方法首先根据类的全名在硬盘上查找类的字节代码文件（.class文件），然后读取该文件内容，最后通过defineClass()方法来把这些字节代码转换成java.lang.Class类的实例。网络类加载器下面将通过一个网络类加载器来说明如何通过类加载器来实现组件的动态更新。即基本的场景是：Java字节代码（.class）文件存放在服务器上，客户端通过网络的方式获取字节代码并执行。当有版本更新的时候，只需要替换掉服务器上保存的文件即可。通过类加载器可以比较简单的实现这种需求。类NetworkClassLoader负责通过网络下载Java类字节代码并定义出Java类。它的实现与FileSystemClassLoader类似。在通过NetworkClassLoader加载了某个版本的类之后，一般有两种做法来使用它。第一种做法是使用Java反射API。另外一种做法是使用接口。需要注意的是，并不能直接在客户端代码中引用从服务器上下载的类，因为客户端代码的类加载器找不到这些类。使用Java反射API可以直接调用Java类的方法。而使用接口的做法则是把接口的类放在客户端中，从服务器上加载实现此接口的不同版本的类。在客户端通过相同的接口来使用这些实现类。网络类加载器的具体代码见下载。在介绍完如何开发自己的类加载器之后，下面说明类加载器和Web容器的关系。回页首类加载器与Web容器对于运行在JavaEE™容器中的Web应用来说，类加载器的实现方式与一般的Java应用有所不同。不同的Web容器的实现方式也会有所不同。以ApacheTomcat来说，每个Web应用都有一个对应的类加载器实例。该类加载器也使用代理模式，所不同的是它是首先尝试去加载某个类，如果找不到再代理给父类加载器。这与一般类加载器的顺序是相反的。这是JavaServlet规范中的推荐做法，其目的是使得Web应用自己的类的优先级高于Web容器提供的类。这种代理模式的一个例外是：Java核心库的类是不在查找范围之内的。这也是为了保证Java核心库的类型安全。绝大多数情况下，Web应用的开发人员不需要考虑与类加载器相关的细节。下面给出几条简单的原则：每个Web应用自己的Java类文件和使用的库的jar包，分别放在WEB-INF/classes和WEB-INF/lib目录下面。多个应用共享的Java类文件和jar包，分别放在Web容器指定的由所有Web应用共享的目录下面。当出现找不到类的错误时，检查当前类的类加载器和当前线程的上下文类加载器是否正确。在介绍完类加载器与Web容器的关系之后，下面介绍它与OSGi的关系。回页首类加载器与OSGiOSGi™是Java上的动态模块系统。它为开发人员提供了面向服务和基于组件的运行环境，并提供标准的方式用来管理软件的生命周期。OSGi已经被实现和部署在很多产品上，在开源社区也得到了广泛的支持。Eclipse就是基于OSGi技术来构建的。OSGi中的每个模块（bundle）都包含Java包和类。模块可以声明它所依赖的需要导入（import）的其它模块的Java包和类（通过Import-Package），也可以声明导出（export）自己的包和类，供其它模块使用（通过Export-Package）。也就是说需要能够隐藏和共享一个模块中的某些Java包和类。这是通过OSGi特有的类加载器机制来实现的。OSGi中的每个模块都有对应的一个类加载器。它负责加载模块自己包含的Java包和类。当它需要加载Java核心库的类时（以java开头的包和类），它会代理给父类加载器（通常是启动类加载器）来完成。当它需要加载所导入的Java类时，它会代理给导出此Java类的模块来完成加载。模块也可以显式的声明某些Java包和类，必须由父类加载器来加载。只需要设置系统属性org.osgi.framework.bootdelegation的值即可。假设有两个模块bundleA和bundleB，它们都有自己对应的类加载器classLoaderA和classLoaderB。在bundleA中包含类com.bundleA.Sample，并且该类被声明为导出的，也就是说可以被其它模块所使用的。bundleB声明了导入bundleA提供的类com.bundleA.Sample，并包含一个类com.bundleB.NewSample继承自com.bundleA.Sample。在bundleB启动的时候，其类加载器classLoaderB需要加载类com.bundleB.NewSample，进而需要加载类com.bundleA.Sample。由于bundleB声明了类com.bundleA.Sample是导入的，classLoaderB把加载类com.bundleA.Sample的工作代理给导出该类的bundleA的类加载器classLoaderA。classLoaderA在其模块内部查找类com.bundleA.Sample并定义它，所得到的类com.bundleA.Sample实例就可以被所有声明导入了此类的模块使用。对于以java开头的类，都是由父类加载器来加载的。如果声明了系统属性org.osgi.framework.bootdelegation=com.example.core.*，那么对于包com.example.core中的类，都是由父类加载器来完成的。OSGi模块的这种类加载器结构，使得一个类的不同版本可以共存在Java虚拟机中，带来了很大的灵活性。不过它的这种不同，也会给开发人员带来一些麻烦，尤其当模块需要使用第三方提供的库的时候。下面提供几条比较好的建议：如果一个类库只有一个模块使用，把该类库的jar包放在模块中，在Bundle-ClassPath中指明即可。如果一个类库被多个模块共用，可以为这个类库单独的创建一个模块，把其它模块需要用到的Java包声明为导出的。其它模块声明导入这些类。如果类库提供了SPI接口，并且利用线程上下文类加载器来加载SPI实现的Java类，有可能会找不到Java类。如果出现了NoClassDefFoundError异常，首先检查当前线程的上下文类加载器是否正确。通过Thread.currentThread().getContextClassLoader()就可以得到该类加载器。该类加载器应该是该模块对应的类加载器。如果不是的话，可以首先通过class.getClassLoader()来得到模块对应的类加载器，再通过Thread.currentThread().setContextClassLoader()来设置当前线程的上下文类加载器。回页首总结类加载器是Java语言的一个创新。它使得动态安装和更新软件组件成为可能。本文详细介绍了类加载器的相关话题，包括基本概念、代理模式、线程上下文类加载器、与Web容器和OSGi的关系等。开发人员在遇到ClassNotFoundException和NoClassDefFoundError等异常的时候，应该检查抛出异常的类的类加载器和当前线程的上下文类加载器，从中可以发现问题的所在。在开发自己的类加载器的时候，需要注意与已有的类加载器组织结构的协调。回页首下载描述名字大小类加载器示例代码classloader.zip13KB参考资料学习“TheJavaLanguageSpecification”的第12章“Execution”和“TheJavaVirtualMachineSpecification”的第5章“Loading,Linking,andInitializing”详细介绍了Java类的加载、链接和初始化。“developerWorks教程：了解JavaClassLoader”：概述了JavaClassLoader，并指导您构造在装入代码之前自动编译代码的示例ClassLoader。“OSGiServicePlatformCoreSpecification”：OSGi规范文档“TheApacheTomcat5.5Servlet/JSPContainer-ClassLoaderHOW-TO”：详细介绍了Tomcat5.5中的类加载器机制。技术书店：浏览关于这些和其他技术主题的图书。developerWorksJava技术专区：数百篇关于Java编程各个方面的文章。获得产品和技术下载IBM软件试用版，体验强大的DB2®，Lotus®，Rational®，Tivoli®和WebSphere®软件。讨论加入developerWorks社区。查看developerWorks博客的最新信息。条评论请登录或注册后发表评论。添加评论:注意：评论中不支持HTML语法有新评论时提醒我剩余1000字符共有评论(29)显示：文章中说到，两个对象obj1和obj2的类的名字相同，但是这两个类是由不同的类加载器实例来加载的，因此不被Java虚拟机认为是相同的。假如我写一个自己的类MyClass，在程序中写这样的语句Objectobj=newMyClass();Object是由Bootstrp加载负责加载，MyClass是由APPClassLoader加载负责加载，两个类是不同加载器加载，为什么却能够这样赋值呢？由lihuayong123于2014年05月27日发布报告滥用清单4的运行结果没有异常自定义的ClassLoader只有findclass并没有defineclass由rogerjiao于2014年02月11日发布报告滥用扩展类加载器的父类加载器是引导类加载器；这句及(类加载器树状组织结构示意图)是有疑问的。对于HotspotJVM来说扩展类加载器并不继承自引导类加载器。由hunter114于2014年02月09日发布报告滥用类加载器是Java语言的一个创新。它使得动态安装和更新软件组件成为可能。由leoljzhang于2013年12月05日发布报告滥用\"更正下，我上面的评论。我理解错作者的意思了。作者说得没有问题。由albeter于2013年09月13日发布报告滥用IBMPureSystemsIBMPureSystems™系列解决方案是一个专家集成系统developerWorks学习路线图通过学习路线图系统掌握软件开发技能软件下载资源中心软件下载、试用版及云计算回页首帮助联系编辑提交内容网站导航订阅源在线浏览每周时事通讯新浪微博报告滥用使用条款第三方提示隐私条约浏览辅助IBM教育学院教育培养计划IBM创业企业全球扶持计划ISV资源(英语)dW中国每周时事通讯IBM®");
				userList.add(user);
			}
		} else if (type == INTEGER) {
			for (int i = 0; i < recordCount; i++) {
				User user = new User();
				user.setId(random.nextInt());
				user.setMale(false);
				user.setPrice(0f);
				user.setNickname("");
				user.setDesc("");
				user.setHomepage("");
				user.setName("");
				userList.add(user);
			}
		} else if (type == FLOAT) {
			for (int i = 0; i < recordCount; i++) {
				User user = new User();
				user.setPrice(random.nextFloat());
				user.setId(0);
				user.setMale(false);
				user.setNickname("");
				user.setDesc("");
				user.setHomepage("");
				user.setName("");
				userList.add(user);
			}
		}
		return userList;
	}

}
