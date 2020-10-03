/* Comments */
package com.geeks;

class StudentM
{
    private String name;
    private int rollno;
    
    public StudentM(String name,int rollo)
    {
        this.name=name;
        this.rollno=rollno;
    }
    
    public String getDetails(java.util.ArrayList<StudentM> studentList)
    {
       return "Name = " + this.name + "\n" + 
               "RollNo = " + this.rollno + "\n";
    }
}

public class ArrayList {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        
       java.util.ArrayList<StudentM> studentList = new java.util.ArrayList<StudentM>();
        studentList.add(new StudentM("Rajesh", 105));
        studentList.add(new StudentM("Ayaansh", 106));
        studentList.add(new StudentM("Neha",107));
        printDetails(studentList);
    }
    
    public static void printDetails(java.util.ArrayList<StudentM> studentList)
    {
        for(Object o : studentList)
        {
            StudentM s = (StudentM) o;
            System.out.println(s.getDetails(studentList));
        }
    }

}
