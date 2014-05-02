/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fouche.titanicbookings;

/**
 *
 * @author Michael Fouche & Ryno Mayer
 */
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;


public class NetworkingClient implements ActionListener
{
    private JMenuItem showAll,fromCt,fromDbn,fromPe,fromG,fromS,fromL,fromNy,fromPa,fromA,fromJ;
    private JMenu helpmenu;
    private JMenuItem exitAction;
    private JMenuItem bthelp;
    
     //CREATE GUI
    private JFrame jf;
    private JPanel topPanel, centerPanel, bottomPanel;
    //TOP PANEL
    private JLabel flightLbl, dateLbl, fromLbl, toLbl, seatsLbl, openLbl;
    public JTextField[][] dataTxt;
    private JButton[] openBtn;
    private int rows;
    private JScrollPane scroll;
    private Double priceForFlight;
    
    //CENTER PANEL
    private String[] yearCboItems, monthCboItems, fromCboItems, toCboItems;
    private JComboBox<String> yearCbo, monthCbo, dayCbo, fromCbo, toCbo;   
    private JButton addFlight;
    private JLabel yearLbl, monthLbl, dayLbl, fromLbl2, toLbl2, addLbl;
    private boolean yearSelected, monthSelected, daySelected, fromSelected, toSelected;
    
    
    //BOTTOM PANEL
    private JButton backBtn, saveBtn;
    private Ticket[] t;
    private Flight[] f;
    private boolean guiCreatedBool;
    private boolean ticketQuantitySelected;
    int amountFlights;
    int amountTickets;    
    //END OF GUI
    
    //TICKET GUI
    private JFrame jfT;
    private JPanel topPanelT,centerPanelT, bottomPanelT;
    private int flightNumberT;
    private int rowsT;
    private boolean guiCreatedTicketBool;
    private int currentActiveflightNumber;
    private int countSeatsTaken;
    private boolean filterCity;
    private String cityToFilter;
    //TOP PANEL
    private JLabel ticketNumTLbl, passNameTLbl, passSurnTLbl, ticketQuantityTLbl,  totalTLbl, actionTLbl;
    public JTextField[][] dataTTxt;
    private JButton[] deleteTBtn;
    private JScrollPane scrollT;
    
    //CENTER PANEL
    private String[] ticketsLeftTCbo;
    private JLabel ticketNumTLbl2, nameTLbl, surnameTLbl, ticketQuantity2TLbl, totalT2Lbl;
    private JComboBox<String> quantityTicTCbo;
    private JTextField ticketNumTTxt, nameTTxt, surnameTTxt, amountTTxt;
    private JButton btnAddTicket;
    
    //BOTTOM PANEL    
    private JButton saveChangesTBtn, cancelFlightTBtn, deleteAllTicketsTBtn, deleteFlightTBtn;
    
    //END OF TICKET GUI
    
    //Reporting
    private boolean reportFlag = true;
    
    //NETWORKING VARIABLES
    private Socket server;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    public NetworkingClient()
    {
        guiCreatedBool = false;
        guiCreatedTicketBool = false;
        try
        {
            // Create socket
            server = new Socket("127.0.0.1", 12345);
        }
        catch (IOException ioe)
        {
            System.out.println("IOException: " + ioe.getMessage());
        }
        String[] yearCboItems = {"Select Year", "2013", "2014", "2015"};
        String[] monthCboItems = { "Select Year"};    
        String[] dayCboItems = {"Select Month"}; 
        String[] fromCboItems = {"Select Departing", "Cape Town", "Johannesburg", "Durban", "Port Elizabeth", "George", "Sydney", "London", "New York", "Paris", "Amsterdam"};
        String[] toCboItems = { "Select departing first"};
        monthSelected = false;
        ticketQuantitySelected = false;
        filterCity = false;
        rows = 0;
        rowsT = 0;
        currentActiveflightNumber = 0;
    }
    
    public NetworkingClient(Flight[] fli, Ticket[] tic, int amountF, int amountT)
    {
        guiCreatedBool = false;
        guiCreatedTicketBool = false;
        ticketQuantitySelected = false;
        filterCity = false;
        f = fli;
        t = tic;
        amountFlights = amountF;
        amountTickets = amountT;
        
        
        try
        {
            // Create socket
            server = new Socket("127.0.0.1", 12345);
        }
        catch (IOException ioe)
        {
            System.out.println("IOException: " + ioe.getMessage());
        }
        String[] yearCboItems = {"Select Year", "2013", "2014", "2015"};
        String[] monthCboItems = { "Select Year"};    
        String[] dayCboItems = {"Select Month"}; 
        String[] fromCboItems = {"Select Departing", "Cape Town", "Johannesburg", "Durban", "Port Elizabeth", "George", "Sydney", "London", "New York", "Paris", "Amsterdam"};
        String[] toCboItems = { "Select departing first"};
        monthSelected = false;
        rows = 0;
        rowsT = 0;
        currentActiveflightNumber = 0;
    }
    //gui class calls this to provide data to call flight panel
    public void updateFlightGuiRecords()
    {
        //request specific details from server
        
        //read details
        
        //call method with the Flight class object and update panel to display flights
        //displayAllFlights();
    }
    
    //gui class calls this to provide data to call ticket panel
    public void updateTicketGuiRecords(int flighNumber)
    {
        
        //request specific details from server
        
        //read details
                
        //call method with the Ticket class object and update panel to display tickets
      
       //addTopPanel();
       //displayAllFlights();
    }
    
    public void initialFlightGui()
    {        
        createFlightGui();
    }
    public void displayFilteredFlights()
    {
        //cityToFilter
        try
        {             
            out.writeObject("Send Filtered Flights");
            out.writeObject(cityToFilter);
            out.flush();
            amountFlights = ((int)in.readObject());
            
            f = new Flight[amountFlights];
            for(int i=0;i<amountFlights;i++)
            {
                f[i] = null;
                f[i] = new com.fouche.titanicbookings.Flight();
                f[i] = (com.fouche.titanicbookings.Flight) in.readObject(); 
            }            
           // System.out.println("SERVER: >> " + (String)in.readObject()+"");
        }
        catch (IOException ioe)
        {
            System.out.println("IO Exception: " + ioe.getMessage());
        }
        catch (ClassNotFoundException cnfe)
        {
            System.out.println("Class not found: " + cnfe.getMessage());
        } 
    }
    public void getAllFlights()
    {
        if(reportFlag){System.out.println("Method getAllFlights Entered");}
        if(!guiCreatedBool)
        {
            if(reportFlag){System.out.println("Method createFlightGui called");}
            createFlightGui();
        }
        //request all flights
        try
        {      
            if(reportFlag){System.out.println("Request from server: Send All Flights");}
            out.writeObject("Send All Flights");    
            out.flush();
            amountFlights = ((int)in.readObject());
            
            f = new com.fouche.titanicbookings.Flight[amountFlights];
            for(int i=0;i<amountFlights;i++)
            {
                f[i] = null;
                f[i] = new com.fouche.titanicbookings.Flight();
                f[i] = (com.fouche.titanicbookings.Flight) in.readObject(); 
            }
            if(reportFlag){System.out.println("Amount Flights: "+amountFlights);}
            //System.out.println("SERVER: >> " + (String)in.readObject()+"");
        }
        catch (IOException ioe)
        {
            System.out.println("IO Exception: " + ioe.getMessage());
        }
        catch (ClassNotFoundException cnfe)
        {
            System.out.println("Class not found: " + cnfe.getMessage());
        }
        if(reportFlag){System.out.println("Method getAllFlights completed");}
    }
    public void displayAllFlights()
    {
        if(!filterCity)
        {
            getAllFlights();
        }
        else
        {
            displayFilteredFlights();
        }
        
        addDataPanel(amountFlights);
        //call update gui to match amount of flights
        
        //SORT
        String datei, datej = "";
        int yeari,yearj = 0;
        int monthi,monthj = 0;
        int dayi, dayj = 0;
        for(int i=0;i<amountFlights-1;i++)
        {
            for(int j=i+1;j<amountFlights;j++)
            {
                datei = f[i].getFlightDate();
                datej = f[j].getFlightDate();
                StringTokenizer tokeni = new StringTokenizer(datei);
                StringTokenizer tokenj = new StringTokenizer(datej);
                dayi = Integer.parseInt(tokeni.nextToken("/"));
                monthi = Integer.parseInt(tokeni.nextToken("/"));
                yeari = Integer.parseInt(tokeni.nextToken("/"));
                dayj = Integer.parseInt(tokenj.nextToken("/"));
                monthj = Integer.parseInt(tokenj.nextToken("/"));
                yearj = Integer.parseInt(tokenj.nextToken("/"));
                if(yeari>yearj)
                {
                    Flight temp = f[i];
                    f[i] = f[j];
                    f[j] = temp;
                }
                else if(yeari==yearj && monthi>monthj)
                {
                    Flight temp = f[i];
                    f[i] = f[j];
                    f[j] = temp;
                }
                else if(yeari==yearj && monthi==monthj &&dayi>dayj)
                {
                    Flight temp = f[i];
                    f[i] = f[j];
                    f[j] = temp;
                }
            }
        }
                
        //insert flights
        for(int i=0;i<amountFlights;i++)
        {
            dataTxt[i][0].setText(f[i].getFlightNumber()+"");
            dataTxt[i][1].setText(f[i].getFlightDate());
            dataTxt[i][2].setText(f[i].getDepartCity());
            dataTxt[i][3].setText(f[i].getArriveCity());
            dataTxt[i][4].setText(f[i].getSeatsAvailable()+" / "+f[i].getSeatSold());
            
            if(f[i].isCancelled())
            {
                for(int j=0;j<5;j++)
                {
                    dataTxt[i][j].setOpaque(true);
                    dataTxt[i][j].setBackground(Color.ORANGE);
                }                
            }
        }
    }

    public void openCommunication()
    {
        try
        {
            out = new ObjectOutputStream(server.getOutputStream());
            out.flush();
            in = new ObjectInputStream(server.getInputStream());
        }
        catch (IOException ioe)
        {
            System.out.println("Connection to the server could not be made. Check that the server is running: " + ioe.getMessage());
        }
    }
    public void closeCommunication()
    {
        try
        {
            out.close();
            in.close();
            server.close();    
        }
        catch (IOException ioe)
        {
            System.out.println("IO Exception: " + ioe.getMessage());
        }
    }
    public void getTableNames()
    {
        //main.tableNames[0] = "";
    }
   /* public void createDB(String name)
    {
        try
        { 
            String msg = "";
            String reply = "";
            
            out.writeObject("Create Tables");
            out.writeObject(name);                       
            out.flush();
            //System.out.println("SERVER: >> " + (String)in.readObject()+"");
        }
        catch (IOException ioe)
        {
            System.out.println("Could not create Tables in database: " + ioe.getMessage());
        }
    }*/
    
    public void giveServerFlightDetails()
    {
        try
        { 
            String msg = "";
            String reply = "";   
            out.writeObject("Load Flights");
            out.writeObject((amountFlights+"").toString());
            for(int i=0;i<amountFlights;i++)
            {
                out.writeObject(f[i]);
            }
            
            out.flush();
            //System.out.println("SERVER: >> " + (String)in.readObject()+"");
            //System.out.println("SERVER: >> " + (String)in.readObject()+"");
                           
        }
        catch (IOException ioe)
        {
            System.out.println("Could not give server flight details: " + ioe.getMessage());
        }
        
    }
   
    public void giveServerTicketDetails()
    {
        // The connection has been established - now send/receive.
        
        try
        { 
            String msg = "";
            String reply = "";
            out.writeObject("Load Tickets");
            out.writeObject((amountTickets+"").toString());
            for(int i=0;i<amountTickets;i++)
            {
                out.writeObject(t[i]);
            }            
            out.flush();
            //System.out.println("SERVER: >> " + (String)in.readObject()+"");
            //System.out.println("SERVER: >> " + (String)in.readObject()+"");
        }
        catch (IOException ioe)
        {
            System.out.println("Could not give server ticket details: " + ioe.getMessage());
        }
    }
    public void createTicketGui(int flightnum)
    {
        flightNumberT = flightnum;
        
        
        jfT = new JFrame("Manage Tickets - Flight: "+flightNumberT);        
        topPanelT = new JPanel(new GridLayout(rowsT+1, 6));         
        centerPanelT = new JPanel(new GridLayout(3,5));
        bottomPanelT = new JPanel();
        
        //addTopPanel();
        
        
        saveChangesTBtn = new JButton("Close Flight Management");
        saveChangesTBtn.addActionListener(this);
        cancelFlightTBtn = new JButton("Cancel/Enable Flight");
        cancelFlightTBtn.addActionListener(this);
        deleteAllTicketsTBtn = new JButton("Delete all Tickets");
        deleteAllTicketsTBtn.addActionListener(this);
        deleteFlightTBtn = new JButton("Delete Flight & Tickets");
        deleteFlightTBtn.addActionListener(this);         
            
        bottomPanelT.add(saveChangesTBtn);
        bottomPanelT.add(cancelFlightTBtn);
        bottomPanelT.add(deleteAllTicketsTBtn);
        bottomPanelT.add(deleteFlightTBtn);        
        
        scrollT = new JScrollPane(topPanelT);
        scrollT.setPreferredSize(new Dimension(800, 350));
        scrollT.setVerticalScrollBarPolicy(scrollT.VERTICAL_SCROLLBAR_ALWAYS);
        jfT.add(scrollT, BorderLayout.NORTH);
        jfT.add(centerPanelT, BorderLayout.CENTER);
        jfT.add(bottomPanelT, BorderLayout.SOUTH);
        jfT.setLocation(100,100);        
        jfT.setSize(800,500);
        jfT.setVisible(true);
        jfT.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); //DISPOSE_ON_CLOSE,  DISPOSE_ON_CLOSE 
        jfT.addWindowListener(new WindowAdapter() 
        {
            @Override
            public void windowClosing(WindowEvent e) 
            {
                int result = JOptionPane.showConfirmDialog(jfT, "Are you sure you would like to close the window?");
                if( result==JOptionPane.OK_OPTION)
                {
                    // NOW we change it to dispose on close..
                    jfT.setDefaultCloseOperation(jfT.DISPOSE_ON_CLOSE);
                    jfT.setVisible(false);
                    jfT.dispose();
                    guiCreatedTicketBool = false;
                }
                jfT.setVisible(true);
            }
        });
    }
    public void addCenterPanelTicket()
    {
        //Center panel
        jfT.remove(centerPanelT);
        centerPanelT = new JPanel(new GridLayout(3,5));
        for(int i=0;i<5;i++)
        {
            JLabel l1 = new JLabel("");
            l1.setOpaque(true);
            l1.setBackground(Color.GRAY);
            centerPanelT.add(l1);
        }
        
        ticketNumTLbl2 = new JLabel("Ticket Number");
        nameTLbl = new JLabel("Name");
        surnameTLbl = new JLabel("Surname");
        ticketQuantity2TLbl = new JLabel("Ticket Quantity");
        totalT2Lbl = new JLabel("Total Amount");
        
      //  centerPanelT.add(ticketNumTLbl2);
        centerPanelT.add(nameTLbl);    
        centerPanelT.add(surnameTLbl);    
        centerPanelT.add(ticketQuantity2TLbl);    
        centerPanelT.add(totalT2Lbl);    
        centerPanelT.add(new JLabel());  
        
       // ticketNumTTxt = new JTextField(10);
        nameTTxt = new JTextField(10);
        surnameTTxt = new JTextField(10);
        amountTTxt = new JTextField(10);
        btnAddTicket = new JButton("Add Ticket");
        btnAddTicket.addActionListener(this);
        
        //centerPanelT.add(ticketNumTTxt);
        centerPanelT.add(nameTTxt);    
        centerPanelT.add(surnameTTxt);  
        //center
        switch(countSeatsTaken)
        {
            case 10:
            {
                String[] ticketsLeftTCbo = {"Flight Full"};
                quantityTicTCbo = new JComboBox<>(ticketsLeftTCbo);
                quantityTicTCbo.addActionListener(this);
                centerPanelT.add(quantityTicTCbo);
            }break;
            case 9:{String[] ticketsLeftTCbo = {"1"};quantityTicTCbo = new JComboBox<>(ticketsLeftTCbo);quantityTicTCbo.addActionListener(this);centerPanelT.add(quantityTicTCbo);}break;
            case 8:{String[] ticketsLeftTCbo = {"1", "2"};quantityTicTCbo = new JComboBox<>(ticketsLeftTCbo);quantityTicTCbo.addActionListener(this);centerPanelT.add(quantityTicTCbo);}break;
            case 7:{String[] ticketsLeftTCbo = {"1", "2", "3"};quantityTicTCbo = new JComboBox<>(ticketsLeftTCbo);quantityTicTCbo.addActionListener(this);centerPanelT.add(quantityTicTCbo);}break;
            case 6:{String[] ticketsLeftTCbo = {"1", "2", "3", "4"};quantityTicTCbo = new JComboBox<>(ticketsLeftTCbo);quantityTicTCbo.addActionListener(this);centerPanelT.add(quantityTicTCbo);}break;
            case 5:{String[] ticketsLeftTCbo = {"1", "2", "3", "4", "5"};quantityTicTCbo = new JComboBox<>(ticketsLeftTCbo);quantityTicTCbo.addActionListener(this);centerPanelT.add(quantityTicTCbo);}break;
            case 4:{String[] ticketsLeftTCbo = {"1", "2", "3", "4", "5","6"};quantityTicTCbo = new JComboBox<>(ticketsLeftTCbo);quantityTicTCbo.addActionListener(this);centerPanelT.add(quantityTicTCbo);}break;
            case 3:{String[] ticketsLeftTCbo = {"1", "2", "3", "4", "5","6","7"};quantityTicTCbo = new JComboBox<>(ticketsLeftTCbo);quantityTicTCbo.addActionListener(this);centerPanelT.add(quantityTicTCbo);}break;
            case 2:{String[] ticketsLeftTCbo = {"1", "2", "3", "4", "5","6","7","8"};quantityTicTCbo = new JComboBox<>(ticketsLeftTCbo);quantityTicTCbo.addActionListener(this);centerPanelT.add(quantityTicTCbo);}break;
            case 1:{String[] ticketsLeftTCbo = {"1", "2", "3", "4", "5","6","7","8","9"};quantityTicTCbo = new JComboBox<>(ticketsLeftTCbo);quantityTicTCbo.addActionListener(this);centerPanelT.add(quantityTicTCbo);}break;
            case 0:{String[] ticketsLeftTCbo = {"1", "2", "3", "4", "5","6","7","8","9","10"};quantityTicTCbo = new JComboBox<>(ticketsLeftTCbo);quantityTicTCbo.addActionListener(this);centerPanelT.add(quantityTicTCbo);}break;
            
        } 
        amountTTxt.setEditable(false);
        centerPanelT.add(amountTTxt);           
        centerPanelT.add(btnAddTicket); 

        jfT.add(centerPanelT, BorderLayout.CENTER);
        jfT.validate();
        jfT.repaint();
        //end center
    }
    public void addTopPanel()
    {
        jfT.remove(scrollT);
        int difference = 10-rowsT;
        if(difference>0)
        {
            topPanelT = new JPanel(new GridLayout(rowsT+1+difference, 6)); 
        }
        else
        {
            topPanelT = new JPanel(new GridLayout(rowsT+1, 6)); 
        }
        
        
        
              
        //end of center panel
        
        ticketNumTLbl = new JLabel("Ticket Number");
        passNameTLbl = new JLabel("Name");
        passSurnTLbl = new JLabel("Surname");
        ticketQuantityTLbl = new JLabel("Quantity");
        totalTLbl = new JLabel("Total Amount");
        actionTLbl = new JLabel("Delete Ticket");
        
        topPanelT.add(ticketNumTLbl);
        topPanelT.add(passNameTLbl);
        topPanelT.add(passSurnTLbl);
        topPanelT.add(ticketQuantityTLbl);
        topPanelT.add(totalTLbl);
        topPanelT.add(actionTLbl);
        
        dataTTxt = new JTextField[rowsT][5];
        deleteTBtn = new JButton[rowsT];
        
        
        for(int i=0;i<rowsT;i++)
        {            
            for(int j=0;j<5;j++)
            {
                dataTTxt[i][j] = new JTextField(10);
                dataTTxt[i][j].setEditable(false);
                topPanelT.add(dataTTxt[i][j]); 
            }
            deleteTBtn[i] = new JButton("Delete Ticket");
            deleteTBtn[i].addActionListener(this);
            topPanelT.add(deleteTBtn[i]);
            
        }
        
        for(int i=0;i<difference;i++)
        {
            for(int j=0;j<6;j++)
            {
                topPanelT.add(new JLabel("")); 
            }
        }
       
        scrollT = new JScrollPane(topPanelT);        
        scrollT.setPreferredSize(new Dimension(800, 350));
        scrollT.setVerticalScrollBarPolicy(scrollT.VERTICAL_SCROLLBAR_ALWAYS);
        jfT.add(scrollT, BorderLayout.NORTH);
        jfT.validate();
        jfT.repaint();
    }
    
        
        
     public void createFlightGui()
    {
        
        jf = new JFrame("Flight Management");
        JMenuBar menuBar = new JMenuBar();    
        jf.setJMenuBar(menuBar);
        JMenu fileMenu = new JMenu("Select Departing Location");
        menuBar.add(fileMenu);
        JMenu helpMenu = new JMenu("File");
        menuBar.add(helpMenu);
        //fromCt, fromDbn,fromPe,fromG,fromS,fromL,fromNy,fromPa,fromA,fromJ;
       //"Cape Town", "Durban", "Port Elizabeth", "George", "Sydney", "London", "New York", "Paris", "Amsterdam"
        fromCt = new JMenuItem("Cape Town");
        fromCt.addActionListener(this);
        fromDbn = new JMenuItem("Durban");
        fromDbn.addActionListener(this);
        fromPe = new JMenuItem("Port Elizabeth");
        fromPe.addActionListener(this);
        fromG = new JMenuItem("George");
        fromG.addActionListener(this);
        fromS = new JMenuItem("Sydney");
        fromS.addActionListener(this);
        fromL = new JMenuItem("London");
        fromL.addActionListener(this);
        fromNy = new JMenuItem("New York");
        fromNy.addActionListener(this);
        fromPa = new JMenuItem("Paris");
        fromPa.addActionListener(this);
        fromA = new JMenuItem("Amsterdam");
        fromA.addActionListener(this);
        fromJ = new JMenuItem("Johannesburg");
        fromJ.addActionListener(this);
        showAll = new JMenuItem("Show All Flights");
        showAll.addActionListener(this);
        exitAction =  new JMenuItem("Exit");
        exitAction.addActionListener(this);
        bthelp = new JMenuItem("Help");
        bthelp.addActionListener(this);
       
        fileMenu.add(showAll);
        fileMenu.addSeparator();
        fileMenu.add(fromCt);
        fileMenu.add(fromDbn);
        fileMenu.add(fromPe);
        fileMenu.add(fromG);
        fileMenu.add(fromS);
        fileMenu.add(fromL);
        fileMenu.add(fromNy);
        fileMenu.add(fromPa);
        fileMenu.add(fromA);
        fileMenu.add(fromJ);
        helpMenu.add(bthelp);
        helpMenu.addSeparator();
        helpMenu.add(exitAction);
        
        guiCreatedBool = true;
        
        
        topPanel = new JPanel(new GridLayout(rows+1, 6)); 
        
        centerPanel = new JPanel(new GridLayout(3,6));
        bottomPanel = new JPanel(new GridLayout(1,2));
        //TOP
        //addDataPanel(2);
        
        String[] yearCboItems = {"Select Year", "2013", "2014", "2015"};        
        String[] monthCboItems = { "Select Year"};    
        String[] dayCboItems = {"Select Month"};        
        String[] fromCboItems = {"Select Departing", "Cape Town", "Johannesburg", "Durban", "Port Elizabeth", "George", "Sydney", "London", "New York", "Paris", "Amsterdam"};
        String[] toCboItems = {"Select departing first"};
        
        //CENTER
        yearCbo = new JComboBox<>(yearCboItems);
        yearCbo.addActionListener(this);
        monthCbo = new JComboBox<>(monthCboItems);
        monthCbo.addActionListener(this);   
        dayCbo = new JComboBox<>(dayCboItems);
        dayCbo.addActionListener(this);   
        fromCbo = new JComboBox<>(fromCboItems);
        fromCbo.addActionListener(this);
        toCbo = new JComboBox<>(toCboItems);
        toCbo.addActionListener(this);
        
        addFlight = new JButton("Add Flight");
        addFlight.addActionListener(this);
        
        yearLbl = new JLabel("Year");
        monthLbl = new JLabel("Month");
        dayLbl = new JLabel("Day");
        fromLbl2 = new JLabel("Departing From");
        toLbl2 = new JLabel("Arriving At");
        addLbl = new JLabel("");
        
        for(int i=0;i<6;i++)
        {
            JLabel l1 = new JLabel("");
            l1.setOpaque(true);
            l1.setBackground(Color.GRAY);
            centerPanel.add(l1);
        }
        
        centerPanel.add(yearLbl);
        centerPanel.add(monthLbl);
        centerPanel.add(dayLbl);
        centerPanel.add(fromLbl2);
        centerPanel.add(toLbl2);
        centerPanel.add(addLbl);
        
        centerPanel.add(yearCbo);
        centerPanel.add(monthCbo);
        centerPanel.add(dayCbo);
        centerPanel.add(fromCbo);
        centerPanel.add(toCbo);
        centerPanel.add(addFlight);
        
        //BOTTOM
        backBtn = new JButton("Back");
        backBtn.addActionListener(this);
        saveBtn = new JButton("Save & Exit");
        saveBtn.addActionListener(this);
        
        bottomPanel.add(backBtn);
        bottomPanel.add(saveBtn);
        
        scroll = new JScrollPane(topPanel);
        scroll.setPreferredSize(new Dimension(800, 350));
        scroll.setVerticalScrollBarPolicy(scroll.VERTICAL_SCROLLBAR_ALWAYS);
        jf.add(scroll, BorderLayout.NORTH);;
        jf.add(centerPanel, BorderLayout.CENTER);
        jf.add(bottomPanel, BorderLayout.SOUTH);
        jf.setLocation(100,100);
        
        jf.setSize(800,510);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); //DISPOSE_ON_CLOSE,  DISPOSE_ON_CLOSE 
        jf.addWindowListener(new WindowAdapter() 
        {
            @Override
            public void windowClosing(WindowEvent e) 
            {
                int result = JOptionPane.showConfirmDialog(jf, "Are you sure you would like to exit?");
                if( result==JOptionPane.OK_OPTION)
                {
                    // NOW we change it to dispose on close..
                    jf.setDefaultCloseOperation(jfT.EXIT_ON_CLOSE);
                    jf.setVisible(false);
                    jf.dispose();
                    guiCreatedTicketBool = false;
                }
                jf.setVisible(true);
            }
        });
    }
    
    //This method is called by the networkClient class
    public void addDataPanel(int r)
    {
        rows = r;
        monthSelected = false;
        jf.remove(scroll);
        int difference = 10-rows;
        if(difference>0)
        {
            topPanel = new JPanel(new GridLayout(rows+1+difference, 6)); 
        }
        else
        {
            topPanel = new JPanel(new GridLayout(rows+1, 6)); 
        }
        
        flightLbl = new JLabel("Flight Number");
        dateLbl = new JLabel("Date");
        fromLbl = new JLabel("Departing From");
        toLbl = new JLabel("Arriving At");
        seatsLbl = new JLabel("Seats Available / Sold");
        openLbl = new JLabel("");
        
        topPanel.add(flightLbl);
        topPanel.add(dateLbl);
        topPanel.add(fromLbl);
        topPanel.add(toLbl);
        topPanel.add(seatsLbl);
        topPanel.add(openLbl);
                
        dataTxt = new JTextField[rows][5];
        openBtn = new JButton[rows];
        
        for(int i=0;i<rows;i++)
        {            
            for(int j=0;j<5;j++)
            {
                dataTxt[i][j] = null;
                dataTxt[i][j] = new JTextField(10);
                dataTxt[i][j].setEditable(false);
                topPanel.add(dataTxt[i][j]); 
            }
            openBtn[i] = null;
            openBtn[i] = new JButton("Open Flight");
            openBtn[i].addActionListener(this);
            topPanel.add(openBtn[i]);
        }
        
        for(int i=0;i<difference;i++)
        {
            for(int j=0;j<6;j++)
            {
                topPanel.add(new JLabel("")); //txtEmpty
            }
        }
        scroll = new JScrollPane(topPanel);
        scroll.setPreferredSize(new Dimension(800, 350));
        scroll.setVerticalScrollBarPolicy(scroll.VERTICAL_SCROLLBAR_ALWAYS);
        jf.add(scroll, BorderLayout.NORTH);
        
        jf.validate();
        jf.repaint();
    }
    
    public void addCenterPanel()
    {
            jf.remove(centerPanel);
            centerPanel = new JPanel(new GridLayout(3,6));
            
            for(int i=0;i<6;i++)
            {
                JLabel l1 = new JLabel("");
                l1.setOpaque(true);
                l1.setBackground(Color.GRAY);
                centerPanel.add(l1);
            }
            
            centerPanel.add(yearLbl);
            centerPanel.add(monthLbl);
            centerPanel.add(dayLbl);
            centerPanel.add(fromLbl2);
            centerPanel.add(toLbl2);
            centerPanel.add(addLbl);
            
            centerPanel.add(yearCbo);
            centerPanel.add(monthCbo);
            centerPanel.add(dayCbo);
            centerPanel.add(fromCbo);
            centerPanel.add(toCbo);
            centerPanel.add(addFlight);
            
            jf.add(centerPanel, BorderLayout.CENTER);
            jf.validate();
            jf.repaint(); 
    }
    
    public void retrieveAllTickets(int currentActiveflightNumber)
    {
        int flightNumber = currentActiveflightNumber;
        boolean flightCancelled = false;
        for(int i=0;i<amountFlights;i++)
        {
            if(f[i].getFlightNumber() ==flightNumber)
            {
                if(f[i].isCancelled())
                {
                    flightCancelled = true;
                }
            }
        }
        try
        {              
            out.writeObject("Send Tickets");   
            out.writeObject(flightNumber+"");   
            out.flush();
            rowsT = (int)in.readObject(); 
        }
        catch(Exception ee)
        {
           System.out.println("Request Ticket and write to GUI1: " + ee);
        }  
        if(guiCreatedTicketBool == false)
        {
            createTicketGui(flightNumber);  
            guiCreatedTicketBool = true;
        }
        
        addTopPanel(); 
        t = new com.fouche.titanicbookings.Ticket[rowsT];
        try
        {
            countSeatsTaken = 0;
            for(int i=0;i<rowsT;i++)
            {
                t[i] = null;
                t[i] = new com.fouche.titanicbookings.Ticket();
                t[i] = (com.fouche.titanicbookings.Ticket)in.readObject();
                       
                
                dataTTxt[i][0].setText(t[i].getTicketNumber()+"");
                dataTTxt[i][1].setText(t[i].getPassengerName());
                dataTTxt[i][2].setText(t[i].getPassengerSurname());
                dataTTxt[i][3].setText(t[i].getSeatsBooked()+"");
                dataTTxt[i][4].setText(t[i].getAmountPaid()+"");                
                
                if(flightCancelled)
                {
                    for(int j=0;j<5;j++)
                    {
                        dataTTxt[i][j].setOpaque(true);
                        dataTTxt[i][j].setBackground(Color.ORANGE);
                    }
                    
                }
                countSeatsTaken= countSeatsTaken+( Integer.parseInt( t[i].getSeatsBooked()+""));
            }
            addCenterPanelTicket();
            //System.out.println("Server: >>"+(String)in.readObject());  
            
        }
        catch(Exception ee)
        {
           System.out.println("Request Ticket and write to GUI2: " + ee);
        } 
        
    }
    
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource()== yearCbo)
        {
            
            switch((String)yearCbo.getSelectedItem())
            {//"Cape Town", "Johannesburg", "Durban", "Port Elizabeth", "George", "Sydney", "London", "New York", "Paris", "Amsterdam"
                case "Select Year":
                {
                    monthSelected = false;
                    String[] monthCboItems = {"Select Year"};   
                    monthCbo = new JComboBox<>(monthCboItems);
                    monthCbo.addActionListener(this); 
                    String[] dayCboItems = {"Select Month"};
                    dayCbo = new JComboBox<>(dayCboItems);
                    dayCbo.addActionListener(this);
                    yearSelected = false;
                    monthSelected = false;
                    daySelected = false;
                    break;                    
                }
                case "2013":
                case "2014":                
                case"2015":
                {
                    monthSelected = true;
                    String[] monthCboItems = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};   
                    monthCbo = new JComboBox<>(monthCboItems);
                    monthCbo.addActionListener(this);
                    yearSelected = true;
                    break;                   
                }
            }
            addCenterPanel();
        }
        if(e.getSource()== monthCbo && monthSelected)
        {
            switch((String)monthCbo.getSelectedItem())
            {
                case "January":
                case "March":
                case "May":
                case "July":
                case "August":
                case "October":
                case "December":
                {
                    String[] dayCboItems = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
                    dayCbo = new JComboBox<>(dayCboItems);
                    dayCbo.addActionListener(this);
                    monthSelected = true;break;
                }
                case "February":
                {
                    String[] dayCboItems = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28"};
                    dayCbo = new JComboBox<>(dayCboItems);
                    dayCbo.addActionListener(this);
                    monthSelected = true;break;
                }                
                case "April":                
                case "June":                
                case "September":                
                case "November":
                {
                    String[] dayCboItems = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30"};
                    dayCbo = new JComboBox<>(dayCboItems);
                    dayCbo.addActionListener(this);
                    monthSelected = true;break;
                }
                
            }
            addCenterPanel();
        }
        if(e.getSource()== dayCbo && monthSelected)
        {            
            if(!((String)dayCbo.getSelectedItem()).equals("Select Month"))
            {
                daySelected = true;
            }
           
        }
        if(e.getSource()== toCbo)
        {
            switch((String)toCbo.getSelectedItem())
            {
                case"Cape Town": case "Johannesburg": case"Durban": case "Port Elizabeth": case"George": case"Sydney": case"London": case"New York": case"Paris": case"Amsterdam":
                {
                    toSelected = true;                    
                }                
            } 
            if(((String)toCbo.getSelectedItem()).equals("Sydney") ||((String)toCbo.getSelectedItem()).equals("London") ||((String)toCbo.getSelectedItem()).equals("New York") ||((String)toCbo.getSelectedItem()).equals("Paris") ||((String)toCbo.getSelectedItem()).equals("Amsterdam") )
            {
                if(priceForFlight==500)
                {
                    priceForFlight = 1000.0;
                }
            }
        }
        if(e.getSource()== fromCbo)
        {
            switch((String)fromCbo.getSelectedItem())
            {//"Cape Town", "Johannesburg", "Durban", "Port Elizabeth", "George", "Sydney", "London", "New York", "Paris", "Amsterdam"
                case "Select Departing":
                { 
                    String[] toCboItems = {"Select departing first"};
                    toCbo = new JComboBox<>(toCboItems);
                    toCbo.addActionListener(this);
                    fromSelected = false;
                    break;
                }               
                case "Cape Town":
                {                    
                    String[] toCboItems = {"Johannesburg", "Durban", "Port Elizabeth", "George", "Sydney", "London", "New York", "Paris", "Amsterdam"};
                    toCbo = new JComboBox<>(toCboItems);
                    toCbo.addActionListener(this);
                    fromSelected = true;
                    priceForFlight = 500.0;
                    break;
                }
                case "Johannesburg": 
                {
                    String[] toCboItems = {"Cape Town", "Durban", "Port Elizabeth", "George", "Sydney", "London", "New York", "Paris", "Amsterdam"};
                    toCbo = new JComboBox<>(toCboItems);
                    toCbo.addActionListener(this);
                    fromSelected = true;
                    priceForFlight = 500.0;
                    break;
                }
                case "Durban": 
                {
                    String[] toCboItems = {"Cape Town", "Johannesburg", "Port Elizabeth", "George"};
                    toCbo = new JComboBox<>(toCboItems);
                    toCbo.addActionListener(this);
                    fromSelected = true;
                    priceForFlight = 500.0;
                    break;
                }
                case "Port Elizabeth": 
                {
                    String[] toCboItems = {"Cape Town", "Johannesburg", "Durban", "George"};
                    toCbo = new JComboBox<>(toCboItems);
                    toCbo.addActionListener(this);
                    fromSelected = true;
                    priceForFlight = 500.0;
                    break;
                }
                case "George": 
                {
                    String[] toCboItems = {"Cape Town", "Johannesburg", "Durban", "Port Elizabeth"};
                    toCbo = new JComboBox<>(toCboItems);
                    toCbo.addActionListener(this);
                    fromSelected = true;
                    priceForFlight = 500.0;
                    break;
                }
                case "Sydney": 
                {
                    String[] toCboItems = {"Cape Town", "Johannesburg", "London", "New York", "Paris", "Amsterdam"};
                    toCbo = new JComboBox<>(toCboItems);
                    toCbo.addActionListener(this);
                    fromSelected = true;
                    priceForFlight = 1000.0;
                    break;
                }
                case "London":
                {
                    String[] toCboItems = {"Cape Town", "Johannesburg", "Sydney", "New York", "Paris", "Amsterdam"};
                    toCbo = new JComboBox<>(toCboItems);
                    toCbo.addActionListener(this);
                    fromSelected = true;
                    priceForFlight = 1000.0;
                    break;
                }
                case "New York": 
                {
                    String[] toCboItems = {"Cape Town", "Johannesburg", "Sydney", "London", "Paris", "Amsterdam"};
                    toCbo = new JComboBox<>(toCboItems);
                    toCbo.addActionListener(this);
                    fromSelected = true;
                    priceForFlight = 1000.0;
                    break;
                }
                case "Paris": 
                {
                    String[] toCboItems = {"Cape Town", "Johannesburg", "Sydney", "London", "New York", "Amsterdam"};
                    toCbo = new JComboBox<>(toCboItems);
                    toCbo.addActionListener(this);
                    fromSelected = true;
                    priceForFlight = 1000.0;
                    break;
                }
                case "Amsterdam": 
                {
                    String[] toCboItems = {"Cape Town", "Johannesburg", "Sydney", "London", "New York", "Paris"};
                    toCbo = new JComboBox<>(toCboItems);
                    toCbo.addActionListener(this);
                    fromSelected = true;
                    priceForFlight = 1000.0;
                    break;
                }
            } 
            addCenterPanel();
        }
        
        if(e.getSource()== addFlight)
        {
            if(yearSelected&& monthSelected&& daySelected&& fromSelected&& toSelected)
            {
                //JOptionPane.showMessageDialog(null, "Item may be added");
                try
                {              
                    out.writeObject("Add Flight");  
                    int dateVal=0;
                    if(((String)monthCbo.getSelectedItem()).equals("January"))
                    {
                        dateVal = 1;
                    }
                    else if(((String)monthCbo.getSelectedItem()).equals("February"))
                    {
                        dateVal = 2;
                    }
                    else if(((String)monthCbo.getSelectedItem()).equals("March"))
                    {
                        dateVal = 3;
                    }else if(((String)monthCbo.getSelectedItem()).equals("April"))
                    {
                        dateVal = 4;
                    }else if(((String)monthCbo.getSelectedItem()).equals("May"))
                    {
                        dateVal = 5;
                    }else if(((String)monthCbo.getSelectedItem()).equals("June"))
                    {
                        dateVal = 6;
                    }else if(((String)monthCbo.getSelectedItem()).equals("July"))
                    {
                        dateVal = 7;
                    }else if(((String)monthCbo.getSelectedItem()).equals("August"))
                    {
                        dateVal = 8;
                    }else if(((String)monthCbo.getSelectedItem()).equals("September"))
                    {
                        dateVal = 9;
                    }else if(((String)monthCbo.getSelectedItem()).equals("October"))
                    {
                        dateVal = 10;
                    }else if(((String)monthCbo.getSelectedItem()).equals("November"))
                    {
                        dateVal = 11;
                    }else if(((String)monthCbo.getSelectedItem()).equals("December"))
                    {
                        dateVal = 12;
                    }
                    String dateString = (String)dayCbo.getSelectedItem()+"/"+dateVal+"/"+(String)yearCbo.getSelectedItem();
                    out.writeObject(dateString+"");  
                    out.writeObject((String)fromCbo.getSelectedItem());
                    out.writeObject((String)toCbo.getSelectedItem());
                    out.writeObject(priceForFlight+"");
                    out.flush();
                    //System.out.println("SERVER: >> "+(String)in.readObject()); 
                    
                    //retrieve all flights
                    
                    
                }
                catch(Exception ee)
                {
                   System.out.println("Request Flight and write to GUI1: " + ee);
                }
                displayAllFlights();
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Please select all the dropdown boxes to add a valid item");
            }
        }
        for(int j=0;j<rows;j++)
        {
            if(e.getSource()==openBtn[j])
            {
                if(guiCreatedTicketBool == true)
                {
                    JOptionPane.showMessageDialog(null, "Please close all open flights, to open another.");
                }
                else
                {
                    currentActiveflightNumber = Integer.parseInt(dataTxt[j][0].getText());  
                    retrieveAllTickets(currentActiveflightNumber);
                    ticketQuantitySelected = false;
                }
                //not send all flights, get all flights... 
                
            }
        }
        if(e.getSource()== backBtn)
        {
            
        }
        if(e.getSource()== saveBtn)
        {
            
        }
        
        if(e.getSource() == btnAddTicket)
        {
            if(!((String)quantityTicTCbo.getSelectedItem()).equals("Flight Full"))
            {
                if(!nameTTxt.getText().equals("") &&!surnameTTxt.getText().equals("") &&!amountTTxt.getText().equals("")&&ticketQuantitySelected==true)
                { 
                    try
                    {  
                        out.writeObject("Add Ticket");
                        out.writeObject(currentActiveflightNumber+"");
                        out.writeObject(nameTTxt.getText()+"");
                        out.writeObject(surnameTTxt.getText()+"");
                        out.writeObject((String)quantityTicTCbo.getSelectedItem()+"");
                        out.writeObject(amountTTxt.getText()+"");
                        out.flush();
                        //System.out.println("SERVER: >> "+(String)in.readObject());
                        //System.out.println("SERVER: >> "+(String)in.readObject());
                        retrieveAllTickets(currentActiveflightNumber);
                        displayAllFlights();
                    }
                    catch(Exception eee)
                    {
                        System.out.println("Send ticket to server: "+eee);
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Please fill in all the fields");
                }                
            }
            else
            {
                JOptionPane.showMessageDialog(null, "This flight is currently full, no more bookings can be made!");
            }
           // out.writeObject("Add Ticket");
            //request to add (amount)
            //server says yes
            //proceed//or cancel
            
           // out.writeObject();
            //get from txts
            
            //check if available
           // send to server
            
            
        }
        if(e.getSource()==quantityTicTCbo)
        {
            if(!((String)quantityTicTCbo.getSelectedItem()).equals("Flight Full"))
            {
                ticketQuantitySelected = true;
                double flightPrice = 0.00;
                for(int i=0;i<amountFlights;i++)
                {
                    if((f[i].getFlightNumber())==currentActiveflightNumber)
                    {
                        flightPrice = f[i].getSeatPrice();
                    }
                }
                amountTTxt.setText((flightPrice*(Integer.parseInt((String)quantityTicTCbo.getSelectedItem())))+"" );
            }
        }
        for(int i=0;i<rowsT;i++)
        {
            if(e.getSource()==deleteTBtn[i])
            {
                try
                {
                    int dialogButton = JOptionPane.YES_NO_OPTION;
                    int dialogResult = JOptionPane.showConfirmDialog (null, "Are you sure you want to delete the ticket?","Warning",dialogButton);
                    if(dialogResult == JOptionPane.YES_OPTION)
                    {
                        
                        int ticketNumber = t[i].getTicketNumber();
                        int seatsBooked = t[i].getSeatsBooked() ;
                        
                        out.writeObject("Delete Ticket");
                        out.writeObject(currentActiveflightNumber+"");
                        out.writeObject(ticketNumber+"");
                        out.writeObject(seatsBooked+"");
                        //System.out.println("SERVER: >> "+(String)in.readObject());
                        //System.out.println("SERVER: >> "+(String)in.readObject());
                        //flightNumber
                        //ticketNumber
                        //seats Booked
                        retrieveAllTickets(currentActiveflightNumber);
                        displayAllFlights();
                    }
                }
                catch(Exception eeee)
                {
                    System.out.println("REQUEST SERVER TO DELETE TICKET"+eeee);
                }
            }
        }
        if(e.getSource() == cancelFlightTBtn)
        {
            try
            {
                out.writeObject("Cancel Flight");
                out.writeObject(currentActiveflightNumber+"");
                out.flush();
                //System.out.println("SERVER: >> "+(String)in.readObject());
                displayAllFlights();
                retrieveAllTickets(currentActiveflightNumber);
                
            }
            catch(Exception eee)
            {
                System.out.println("REQUEST SERVER TO CHANGE FLIGHT STATUS"+eee);
            }
        }
        if(e.getSource() == deleteAllTicketsTBtn)
        {
            try
            {
                int dialogButton = JOptionPane.YES_NO_OPTION;
                int dialogResult = JOptionPane.showConfirmDialog (null, "Are you sure you want to delete all the tickets in this flight?","Warning",dialogButton);
                if(dialogResult == JOptionPane.YES_OPTION)
                {
                    out.writeObject("Delete all Tickets");
                    out.writeObject(currentActiveflightNumber+"");
                    out.flush();
                    //System.out.println("SERVER: >> "+(String)in.readObject());
                    //System.out.println("SERVER: >> "+(String)in.readObject());
                    displayAllFlights();
                    retrieveAllTickets(currentActiveflightNumber);
                }
            }
            catch(Exception eeee)
            {
                System.out.println("REQUEST SERVER TO DELETE TICKET"+eeee);
            }
        }
        if(e.getSource() == deleteFlightTBtn)
        {
            try
            {
                int dialogButton = JOptionPane.YES_NO_OPTION;
                int dialogResult = JOptionPane.showConfirmDialog (null, "Are you sure you want to delete the flight, and all the tickets with it?","Warning",dialogButton);
                if(dialogResult == JOptionPane.YES_OPTION)
                {                    
                    out.writeObject("Delete Flight");
                    out.writeObject(currentActiveflightNumber+"");
                    out.flush();
                    //System.out.println("SERVER: >> "+(String)in.readObject());
                    //System.out.println("SERVER: >> "+(String)in.readObject());
                    jfT.dispose();
                    guiCreatedTicketBool = false;
                    displayAllFlights();
                }
            }
            catch(Exception eeee)
            {
                System.out.println("REQUEST SERVER TO DELETE TICKET"+eeee);
            }
        }
        if(e.getSource()==showAll)
        {
            filterCity = false;
            displayAllFlights();
            //showAll,fromCt, fromDbn,fromPe,fromG,fromS,fromL,fromNy,fromPa,fromA,fromJ;
            //"Cape Town", "Durban", "Port Elizabeth", "George", "Sydney", "London", "New York", "Paris", "Amsterdam"
        }
        if(e.getSource()==fromCt)
        {
            filterCity = true;
            cityToFilter = "Cape Town";
            displayAllFlights();
        }
        if(e.getSource()==fromDbn)
        {
            filterCity = true;
            cityToFilter = "Durban";
            displayAllFlights();
        }
        if(e.getSource()==fromPe)
        {
            filterCity = true;
            cityToFilter = "Port Elizabeth";
            displayAllFlights();
        }
        if(e.getSource()==fromG)
        {
            filterCity = true;            
            cityToFilter = "George";
            displayAllFlights();
        }
        if(e.getSource()==fromS)
        {
            filterCity = true;
            cityToFilter = "Sydney";
            displayAllFlights();
        }
        if(e.getSource()==fromL)
        {
            filterCity = true;
            cityToFilter = "London";
            displayAllFlights();
        }
        if(e.getSource()==fromNy)
        {
            filterCity = true;
            cityToFilter = "New York";
            displayAllFlights();
        }
        if(e.getSource()==fromPa)
        {
            filterCity = true;
            cityToFilter = "Paris";
            displayAllFlights();
        }
        if(e.getSource()==fromA)
        {
            filterCity = true;
            cityToFilter = "Amsterdam";
            displayAllFlights();
        }
        if(e.getSource()==fromJ)
        {
            filterCity = true;
            cityToFilter = "Johannesburg";
            displayAllFlights();
        }
        if(e.getSource() == exitAction)
        {
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog (null, "Are you sure you want to exit?","Warning",dialogButton);
            if(dialogResult == JOptionPane.YES_OPTION)
            {
                System.exit(0);
            }
        }
        if(e.getSource() == bthelp)
        {
            JFrame jfhelp = new JFrame("Help");
            JTextArea txt = new JTextArea(30,15);
            txt.setWrapStyleWord(true); 
            txt.setText("~~~~~~~~~ TITANIC BOOKINGS HELP FILE ~~~~~~~~~\n");
            txt.append("FLIGHTS - A user can view, create, delete and cancel flights.\n");
            txt.append("-ADD FLIGHT: To add a flight, select the dropdown menus at the bottom\n");
            txt.append("of the main window, once all have been selected click on 'Add Flight'\n\n");
            txt.append("-DELETE FLIGHT: To delete a flight, click on the 'Open Flight' button next\n");
            txt.append("to the flight. Once the manage flight window has opened, click on \n");
            txt.append("'Delete Flight & Tickets' to delete the flight with it's accompanying tickets\n\n");
            txt.append("-CANCEL FLIGHT: To cancel a flight, click on the 'Open Flight' button next\n");
            txt.append("to the flight. Once the manage flight window has opened, click on\n");
            txt.append("'Cancel/Enable flight' to cancel or re-enable the flight with it's \n");
            txt.append("accompanying tickets. (A Flight and it's tickets will have a orange\n");
            txt.append("background when it has been cancelled)\n\n");
            txt.append("-----------------------------------------------------------------------------\n");
            txt.append("TICKETS: A user can view, add, or delete tickets for a specific flight\n");
            txt.append("or delete all the tickets from a flight.\n\n");
            txt.append("-ADD TICKET: To add a ticket, open a specific flight by clicking on the\n");
            txt.append("'Open Flight' button next to the flight. once the manage flight window\n");
            txt.append("has opened, fill-in the textboxes at the bottom of the window.\n");
            txt.append("Once completed click on 'Add Ticket'\n\n");
            txt.append("-DELETE TICKET: To delete a ticket open a specific flight by clicking on\n");
            txt.append("the 'Open Flight' button next to the flight. once the manage flight window\n");
            txt.append("has opened, Click on the 'Delete Ticket' Button.\n\n");
            txt.append("DELETE ALL TICKETS: To delete all the tickets from a specific flight,\n");
            txt.append("open a specific flight by clicking on the 'Open Flight' button next to the\n");
            txt.append("flight. once the manage flight window has opened, Click on the\n");
            txt.append("'Delete all Tickets' Button to delete all the tickets.\n\n");
            txt.append("");
            jfhelp.setResizable(false);
            txt.setEditable(false);
            jfhelp.add(txt);
            jfhelp.setLocation(100,100);        
            jfhelp.setSize(420,580);
            jfhelp.setVisible(true);
            jfhelp.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        }
        if(e.getSource() ==saveChangesTBtn)
        {
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog (null, "Are you sure you want to close the window?","Warning",dialogButton);
            if(dialogResult == JOptionPane.YES_OPTION)
            {
                jfT.dispose();
                guiCreatedTicketBool = false;
            }
        }
        if(e.getSource() == saveBtn)
        {
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog (null, "Are you sure you want to exit?","Warning",dialogButton);
            if(dialogResult == JOptionPane.YES_OPTION)
            {
                System.exit(0);
            }
        }
    
    }
}
