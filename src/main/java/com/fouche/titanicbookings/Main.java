/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fouche.titanicbookings;

/**
 *
 * @author foosh
 */
import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
public class Main 
{
    private BufferedReader br;
    private BufferedWriter bw;
    private FileReader fr;
    private FileWriter fw;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private int numFlightRecords,numTicketRecords;
    
    private Ticket[] t;
    private Flight[] f;
    public String[] tableNames;
    
    //MAIN GUI
    private JFrame jf;
    private JPanel centerPanel, bottomPanel;
    private JLabel tblNameLbl;
    private JTextField tblNameTxt;
    private JButton createBtn;
    
    NetworkingClient ncObject;
    
    public Main()
    { 
          f = new Flight[1000];
          t = new Ticket[1000];
          tableNames = new String[100];  
          //this.readSerFlight();
          //this.readSerTicket();
          //  m.writeSerFlight();
          
          ncObject = new NetworkingClient(this.f, this.t,this.numFlightRecords, this.numTicketRecords);
          ncObject.openCommunication();
      
      
    }    
    
    
  /*  public void createMainGui()
    {
        jf = new JFrame();
        centerPanel = new JPanel(new GridLayout(1,2));
        bottomPanel = new JPanel(new GridLayout(1,1));
        tblNameLbl = new JLabel("Tabel Name: ");
        tblNameTxt = new JTextField(10);
        createBtn = new JButton("Create & Load Table");
        createBtn.addActionListener(this);
        
        centerPanel.add(tblNameLbl);
        centerPanel.add(tblNameTxt);
        bottomPanel.add(createBtn);
        
        jf.add(centerPanel,BorderLayout.CENTER);
        jf.add(bottomPanel,BorderLayout.SOUTH);
        jf.setLocation(200,200);
        jf.pack();
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //DISPOSE_ON_CLOSE,  DISPOSE_ON_CLOSE 
    }
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource()==createBtn)
        {
            if(!tblNameTxt.getText().equals(""))
            { 
                try
                {
                    ncObject.createDB(tblNameTxt.getText());
                    ncObject.getAllFlights();
                    ncObject.displayAllFlights();
                    jf.dispose();
                }
                catch(Exception ee)
                {
                   System.out.println("The program could not be started "+ee);
                }
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Please enter a valid tablename in the textbox provided");
            }
        }
    }*/
    
    /*public void display()
    {
        for(int i=0;i<numFlightRecords;i++)
        {
            System.out.print(f[i].toString());
        }
        for(int i=0;i<numTicketRecords;i++)
        {
            System.out.print(t[i].toString());
        }
    }*/
   /* public void readSerFlight()
    {
        try
        {
            input = new ObjectInputStream(new FileInputStream("Flights.ser"));
            numFlightRecords =0;            
            while(true)
            {                
                f[numFlightRecords] = new Flight();
                f[numFlightRecords] = (Flight) input.readObject();
                numFlightRecords++;                
            } 
             
        }        
        catch(ClassNotFoundException cnfe)
        {
            JOptionPane.showMessageDialog(null, "The Flights file was not found!");            
            try{input.close();}catch(Exception e){}
        }
        catch(EOFException eof)
        {
            try{input.close();}catch(Exception e){}
            
            return;            
        }
        catch(IOException e)
        {
            JOptionPane.showMessageDialog(null, "Error while reading from serialized Flight object: \n"+e);
            try{input.close();}catch(Exception ee){}
        }               
    }*/
   /*public void writeSerFlight()
    {
        try
        {
            output = new ObjectOutputStream(new FileOutputStream("FlightTest.ser"));
            for(int i=0;i<numFlightRecords;i++)
            {
                output.writeObject(f[i]);
            }
            output.close();
        }
        catch(IOException e)
        {
            JOptionPane.showMessageDialog(null, "Error while writing to serialized file: \n"+e);
        }
    }*/
    /*public void readSerTicket()
    {
        try
        {
            input = new ObjectInputStream(new FileInputStream("Tickets.ser"));
            numTicketRecords =0;            
            while(true)
            {                
                t[numTicketRecords] = new Ticket();
                t[numTicketRecords] = (Ticket) input.readObject();
                numTicketRecords++;                
            } 
            
        }        
        catch(ClassNotFoundException cnfe)
        {
            JOptionPane.showMessageDialog(null, "The Flights file was not found!");            
            try{input.close();}catch(Exception e){}
        }
        catch(EOFException eof)
        {
            try{input.close();}catch(Exception e){}
            return;            
        }
        catch(IOException e)
        {
            JOptionPane.showMessageDialog(null, "Error while reading from serialized Flight object: \n"+e);
            try{input.close();}catch(Exception ee){}
        }       
    }*/
    
    public static void main(String[]args)
    {
        Main m = new Main();
        m.ncObject = new NetworkingClient();
        m.ncObject.openCommunication();
        m.ncObject.getAllFlights();
        m.ncObject.displayAllFlights();
    }
    
}
