package myfilemod2;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.FileSystems;
import java.nio.file.WatchService;
import java.nio.file.WatchKey;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;

import static java.nio.file.StandardWatchEventKinds.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.io.*;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;



    
public class WatchEventExample {
	
	
	private static char  dirtxt;
	private static String  m  ;
	
	
	
	
     

	
 
    public static void main(String [] args)
            throws Exception {
    	
    
    	
        new WatchEventExample().doWatch();
    }

 
    @SuppressWarnings("unchecked")  
    private void doWatch()
            throws IOException, InterruptedException {
     
//////////////////////////////////
    	
    	
JFrame a = new JFrame("Окно сообщений");
a.setSize(700,300);
a.setLayout(null);
JLabel messLabel = new JLabel("Изменение в каталоге");
messLabel.setBounds(40,90, 400,30); 
a.add(messLabel);
JLabel fileLabel = new JLabel(" ");
fileLabel.setBounds(40,120, 400,30); 
a.add(fileLabel);
JButton b = new JButton("Продолжить");
b.setBounds(150,200,185,20);


b.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
         a.setState(JFrame.ICONIFIED);   
         messLabel.setText("  ");
    }
});
a.add(b);

a.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	


    	
        WatchService watchService = FileSystems.getDefault().newWatchService();
        
      
    	Properties property = new Properties();
    	
        FileInputStream fis = new FileInputStream("dirpath.properties");
       /// Properties property = null;
        property.load(fis);
        String mdirtxt = property.getProperty("db.path");
		
        System.out.println("Watch "+fis);
      
        String dirpath = property.getProperty("db.path");
       Path path = Paths.get(dirpath);
      
        WatchKey watchKey = path.register(watchService,ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
                                     
        System.out.println("Watch service registered dir: " + path.toString());
      ///  messLabel.setText("Изменение в каталоге:"+" "+path.toString());
                                     
        for (;;) {
         
            WatchKey key; 
 
            try {
                System.out.println("Waiting for key to be signalled...");
                key = watchService.take();
            }
            catch (InterruptedException ex) {
                System.out.println("Interrupted Exception");
                return;
            }
             
            List<WatchEvent<?>> eventList = key.pollEvents();
            System.out.println("Process the pending events for the key: " + eventList.size());
 
            for (WatchEvent<?> genericEvent: eventList) {
 
                WatchEvent.Kind<?> eventKind = genericEvent.kind();
                System.out.println("Event kind: " + eventKind);
 
                if (eventKind == OVERFLOW) {
 
                    continue; // pending events for loop
                }
 
                WatchEvent pathEvent = (WatchEvent) genericEvent;
                Path file = (Path) pathEvent.context();
                System.out.println("File name: " + file.toString());
                fileLabel.setText("Изменен файл:"+" " +file.toString());
                
                ////messLabel.setText(file.toString());
               /// messLabel.setText("Получены новые письма");
                messLabel.setText("Изменение в каталоге:"+" "+path.toString());
                a.setState(JFrame.NORMAL);
                		
              
                
          
            } 
            
            
 
            boolean validKey = key.reset();
            System.out.println("Key reset");
            System.out.println("");
            
            a.setVisible(true);
 
            if (! validKey) {
                System.out.println("Invalid key");
                break; // infinite for loop
            }
 
        } // end infinite for loop
       
        System.out.println("Watch service closed.");
    }   
}