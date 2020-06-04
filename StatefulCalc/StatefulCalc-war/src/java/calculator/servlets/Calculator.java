/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculator.servlets;

import calculator.ejb.CalculatorBeanLocal;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author darom
 */
@WebServlet(name = "Calculator", urlPatterns = {"/Calculator"})
public class Calculator extends HttpServlet {

    CalculatorBeanLocal calculatorBean = lookupCalculatorBeanLocal();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        double n1=0;
        if(request.getParameter("n1") != null) {
            n1 = Double.parseDouble(request.getParameter("n1"));
        }
        double n2 = 0;
        if(request.getParameter("n2") != null) {
            n2 = Double.parseDouble(request.getParameter("n2"));
        }
//        String operation = request.getParameter("op");
        double value = 0;
//        if(operation != null && operation.equals("add")) {
//            value += calculatorBean.add(n1, n2);
//        }
//        if(operation != null && operation.equals("subtract")) {
//            value += calculatorBean.subtract(n1, n2);
//        }
//        if(operation != null && operation.equals("multiply")) {
//            value += calculatorBean.multiply(n1, n2);
//        }
//        if(operation != null && operation.equals("divide")) {
//            value += calculatorBean.divide(n1, n2);
//        }
        
        if (request.getParameter("Add") != null) {
            value = calculatorBean.add(n1, n2);
        }
        if (request.getParameter("Sub") != null) {
            value += calculatorBean.subtract(n1, n2);
        }
        if (request.getParameter("Div") != null) {
            value += calculatorBean.divide(n1, n2);
        }
        if (request.getParameter("Mul") != null) {
            value += calculatorBean.multiply(n1, n2);
        }
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            out.println("<form name=\"Calculator\" method=\"post\" action=\"Calculator\">");
            out.println("<input type=\"text\" name=\"n1\" value=\"" + value + "\"/>");
            out.println("<br><br>");
            out.println("<input type=\"text\" name=\"n2\" value=\"\"/>");
            out.println("<br><br>");
            out.println("<input type=\"button\"  value=\" 1 \" onClick=\"document.Calculator.n2.value+='1'\"/>");
            out.println("<input type=\"button\"  value=\" 2 \" onClick=\"document.Calculator.n2.value+='2'\"/>");
            out.println("<input type=\"button\"  value=\" 3 \" onClick=\"document.Calculator.n2.value+='3'\"/>");
            out.println("<input type=\"submit\" name =\"Add\" value=\" + \"/>");
            out.println("<br><br>");
            out.println("<input type=\"button\"  value=\" 4 \" onClick=\"document.Calculator.n2.value+='4'\"/>");
            out.println("<input type=\"button\"  value=\" 5 \" onClick=\"document.Calculator.n2.value+='5'\"/>");
            out.println("<input type=\"button\"  value=\" 6 \" onClick=\"document.Calculator.n2.value+='6'\"/>");
            out.println("<input type=\"submit\" name =\"Sub\" value=\" - \"/>");
            out.println("<br><br>");
            out.println("<input type=\"button\"  value=\" 7 \" onClick=\"document.Calculator.n2.value+='7'\"/>");
            out.println("<input type=\"button\"  value=\" 8 \" onClick=\"document.Calculator.n2.value+='8'\"/>");
            out.println("<input type=\"button\"  value=\" 9 \" onClick=\"document.Calculator.n2.value+='9'\"/>");
            out.println("<input type=\"submit\" name =\"Div\" value=\" / \"/>");
            out.println("<br><br>");
            out.println("<input type=\"button\"  value=\" 0 \" onClick=\"document.Calculator.n2.value+='0'\"/>");
            out.println("<input type=\"button\"  value=\" . \" onClick=\"document.Calculator.n2.value+='.'\"/>");
            out.println("<input type=\"button\" value=\"C\" onClick=\"document.Calculator.n1.value='0'\"/>");
            out.println("<input type=\"submit\" name =\"Mul\" value=\" * \"/>");
            out.println("<br><br>");
            out.println("</form>");
        }
        catch (Exception ex) {
            out.println("Error:"+
                    ex.getMessage());
        }
        finally {
            out.close();
        }
   }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private CalculatorBeanLocal lookupCalculatorBeanLocal() {
        try {
            Context c = new InitialContext();
            return (CalculatorBeanLocal) c.lookup("java:global/StatefulCalc/StatefulCalc-ejb/CalculatorBean!calculator.ejb.CalculatorBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
