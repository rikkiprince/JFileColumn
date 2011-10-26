import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;

import javax.swing.JFileChooser;

import com.sun.xml.internal.ws.message.ByteArrayAttachment;


public class GetPackage extends ClassLoader implements FilenameFilter
{
	
	public static void main(String[] args) throws Exception
	{
		File rootDir = new File("/Users/rprince/robocode/robots");
		JFileChooser chooser = new JFileChooser(rootDir);
		//chooser.setCurrentDirectory(new File("."));
		chooser.setDialogTitle("Select root directory of submissions...");
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		
		if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
		{
			rootDir = chooser.getSelectedFile();
			System.out.println(rootDir);
		}
		else
			throw new Exception("You must select a directory.");
		
		File d = rootDir.getParentFile();
		GetPackage gp = new GetPackage();
		File[] files = d.listFiles(gp);
		for(File f : files)
		{
			Class cls = gp.getCls(f);
			System.out.println(f);
			System.out.println(cls);
			System.out.println(cls.getPackage());
			System.out.println(cls.getSuperclass());
			System.out.println();
		}
	}
	
	public Class getCls(File f) throws IOException
	{
		FileInputStream fis = new FileInputStream(f);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		byte[] buf = new byte[1024];
		int left = 0;
		while((left = fis.available()) > 0)
		{
			int r = fis.read(buf);
			baos.write(buf, 0, r);
		}
		
		buf = baos.toByteArray();
		Class cls = defineClass(null, buf, 0, buf.length);
		
		return cls;
	}

	@Override
	public boolean accept(File dir, String name)
	{
		if(name.endsWith(".class") || name.endsWith(".javaapplet"))
		{
			return true;
		}
		else
			return false;
	}
}
