/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dictionary;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.util.Arrays;
/**
 *
 * @author VAN
 */
public class Dictionary {
     
    private JFrame mainFrame;
    private JLabel headerLabel;
    private JLabel statusLabel;
    private JPanel controlPanel;

    private String word;
    private String meaning;
    
    private NodeList nodeList;
    private List<Dictionary> List;
    
    public String getWord()
    {
      return this.word;  
    }
    public void setWord(String Word)
    {
        this.word=Word;
    }
    
    public String getMeaning()
    {
        return this.meaning;
    }
    public void setMeaning(String Meaning)
    {
        this.meaning=Meaning;
    }
    @Override
    public String toString()
    {
        return "Dictionary:: word=" + this.word + "meaning:"+ this.meaning;
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        Dictionary dictionary = new Dictionary();
          String file ="Anh_Viet.xml";
          File XMLfile = new File(file);
          DocumentBuilderFactory dbFactory= DocumentBuilderFactory.newInstance();
          ReadDictionary(file,XMLfile,dbFactory,dictionary);
         
          dictionary.DictionaryInterface(dictionary);
           
   
    }
    
    private void prepareGUI()
    {
       
        mainFrame = new JFrame("Dictionary");
        mainFrame.setSize(600,600);
        mainFrame.setLayout(new GridLayout(3,1));
        mainFrame.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent windowEvent)
            {
             System.exit(0);   
            }
            
        });
        headerLabel= new JLabel("",JLabel.CENTER);
        statusLabel= new JLabel("",JLabel.CENTER);
        statusLabel.setSize(350,100);
        controlPanel= new JPanel();
        controlPanel.setLayout(new FlowLayout());
        mainFrame.add(headerLabel);
        mainFrame.add(controlPanel);
        mainFrame.add(statusLabel);
        mainFrame.setVisible(true);
    }
    
    private static boolean Search(Dictionary a,String data)
    {
        for(Dictionary dict: a.List)
        {
            if(a.List.contains(data))
            {
                return true;
            }
        }
        return false;
    }
    
    private  void DictionaryInterface(Dictionary a)
    {
        prepareGUI();
        headerLabel.setText("Van 's dictionary");
        final JTextField SearchText = new JTextField(40);
        JButton submit = new JButton("Search");
        submit.addActionListener(new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent e) {
            String data = SearchText.getText();
         for(Dictionary dict: a.List)
        {
            if(dict.word.equals(data))
            {
                statusLabel.setText(dict.getMeaning());
            }
        }
        }
        });
        controlPanel.add(SearchText);
        controlPanel.add(submit);
        mainFrame.setVisible(true);
    }
    
    private static void ReadDictionary(String file,File XMLfile,DocumentBuilderFactory dbFactory,Dictionary a)
    {
        DocumentBuilder dBuilder;
    try
    {
        dBuilder =dbFactory.newDocumentBuilder();
        Document doc=dBuilder.parse(XMLfile);
        doc.getDocumentElement().normalize();
        
        a.nodeList = doc.getElementsByTagName("record");
        a.List= new ArrayList<Dictionary>();
        for(int i=0;i<a.nodeList.getLength();i++)
        {
            a.List.add(getDictionary(a.nodeList.item(i)));
        }
        for(Dictionary dict : a.List)
        {
            System.out.println(dict.word +"="+" "+dict.meaning.replace('=', ':'));
        }
       
    }catch(SAXException | ParserConfigurationException | IOException e1)
    {
        e1.printStackTrace();
    }
    }
    
    
    
    private static Dictionary getDictionary(Node node)
    {
        Dictionary dic = new Dictionary();
        if(node.getNodeType()==Node.ELEMENT_NODE)
        {
            Element dictionary = (Element) node;
            dic.setWord(getTagValue("word",dictionary));
            dic.setMeaning(getTagValue("meaning",dictionary));
        }
        return dic;
    }
    private static String getTagValue(String tag,Element dictionary)
    {
        NodeList nodelist = dictionary.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node)nodelist.item(0);
        return node.getNodeValue();
    }
    
} 
