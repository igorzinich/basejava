package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

public class ResumeServlet extends HttpServlet {

    private Storage storage = Config.get().getStorage();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
//        response.setHeader("Content-Type", "text/html; charset=UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        Writer writer = response.getWriter();
        writer.write("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<style>\n" +
                "table, th, td {\n" +
                "  border:1px solid black;\n" +
                "}\n" +
                "</style>\n" +
                "<body>\n" +
                "\n" +
                "<h2>Список резюме</h2>\n" +
                "\n" +
                "<table style=\"width:30%\">\n" +
                "  <tr>\n" +
                "    <th>uuid</th>\n" +
                "    <th>fullName</th>\n" +
                "  </tr>\n");
        for (Resume resume : storage.getAllSorted()) {
            writer.write("  <tr>\n" +
                    "    <td>" + resume.getUuid() + " </td>\n" +
                    "    <td>" + resume.getFullName() + "</td>\n" +
                    "  </tr>\n");
        }
        writer.write("</table>\n" +
                "\n" +
                "</body>\n" +
                "</html>\n");
    }
}