package com.geeks;

import java.util.ArrayList;
import java.util.List;


class std
{
    private String name;
    private int rollNo;
    
    public std(String name,int rollNo)
    {
        this.name=name;
        this.rollNo=rollNo;
    }
    
    public String getDetails()
    {
        return "Name    = " + this.name + "\n" +
               "RollNo  = " + this.rollNo + "\n";
    }
}
public class ListIterator {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
         
        List<std> studentList = new ArrayList<std>();
        studentList.add(new std("Rajesh",105));
        studentList.add(new std("Rahul",104));
        studentList.add(new std("Praveen",100));
        studentList.add(new std("Randhir",106));
        studentList.add(new std("Ritesh",109));
        
        printList(studentList);

    }
    
    public static void printList(List<std> students)
    {
        java.util.ListIterator<std> it = students.listIterator();
        while(it.hasNext())
        {
            System.out.println(it.next().getDetails());
        }
    }

}
