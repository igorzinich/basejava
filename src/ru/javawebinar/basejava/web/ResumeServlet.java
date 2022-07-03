package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.storage.Storage;
import ru.javawebinar.basejava.util.DateUtil;
import ru.javawebinar.basejava.util.HtmlUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResumeServlet extends HttpServlet {

    private Storage storage = Config.get().getStorage();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");

        Resume r;
        if (uuid == null || uuid.length() == 0) {
            r = new Resume(fullName);
        } else {
            r = storage.get(uuid);
            r.setFullName(fullName);
        }

        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                r.addContact(type, value);
            } else {
                r.getContacts().remove(type);
            }
        }
        for (SectionType type : SectionType.values()) {
            String value = request.getParameter(type.name());
            String[] values = request.getParameterValues(type.name());
            if (HtmlUtil.isEmpty(value) && values.length < 2) {
                r.getSections().remove(type);
            } else {
                switch (type) {
                    case PERSONAL:
                    case OBJECTIVE:
                        r.addSection(type, new StringSection(value));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        List<String> collection = new ArrayList<>(Arrays.asList(value.split("\\n")));
                        collection.removeAll(Arrays.asList("\r", ""));
                        r.addSection(type, new ListSection(collection));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        List<Organization> organizations = new ArrayList<>();
                        String[] urls = request.getParameterValues(type.name() + "url");
                        for (int i = 0; i < values.length; i++) {
                            String name = values[i];
                            if (!HtmlUtil.isEmpty(name)) {
                                String index = type.name() + i;
                                List<Organization.Position> positions = new ArrayList<>();
                                String[] titles = request.getParameterValues(index + "title");
                                String[] startDates = request.getParameterValues(index + "startDate");
                                String[] endDates = request.getParameterValues(index + "endDate");
                                String[] descriptions = request.getParameterValues(index + "description");
                                for (int j = 0; j < titles.length; j++) {
                                    if (!HtmlUtil.isEmpty(titles[j])) {
                                        positions.add(new Organization.Position(DateUtil.parse(startDates[j]), DateUtil.parse(endDates[j]), titles[j], descriptions[j]));
                                    }
                                }
                                organizations.add(new Organization(new Link(name, urls[i]), positions));
                            }
                        }
                        r.addSection(type, new OrganizationSection(organizations));
                        break;
                }
            }
        }
        if (uuid == null || uuid.length() == 0) {
            storage.save(r);
        } else {
            storage.update(r);
        }
        response.sendRedirect("resume");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume r;
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
                r = storage.get(uuid);
                break;
            case "add":
                r = new Resume();
                r.addSection(SectionType.PERSONAL, new StringSection(""));
                r.addSection(SectionType.OBJECTIVE, new StringSection(""));
                r.addSection(SectionType.ACHIEVEMENT, new ListSection(""));
                r.addSection(SectionType.QUALIFICATIONS, new ListSection(""));
                r.addSection(SectionType.EXPERIENCE, new OrganizationSection(Organization.EMPTY));
                r.addSection(SectionType.EDUCATION, new OrganizationSection(Organization.EMPTY));
                break;
            case "edit":
                r = storage.get(uuid);
                for (SectionType type : SectionType.values()) {
                    AbstractSection section = r.getSection(type);
                    switch (type) {
                        case PERSONAL:
                        case OBJECTIVE:
                            if (section == null) {
                                section = new StringSection("");
                            }
                            break;
                        case ACHIEVEMENT:
                        case QUALIFICATIONS:
                            if (section == null) {
                                section = new ListSection("");
                            }
                            break;
                        case EXPERIENCE:
                        case EDUCATION:
                            OrganizationSection organizationSection = (OrganizationSection) section;
                            List<Organization> emptyFirstOrganizations = new ArrayList<>();
                            emptyFirstOrganizations.add(Organization.EMPTY);
                            if (organizationSection != null) {
                                for (Organization organization : organizationSection.getListOrganizations()) {
                                    List<Organization.Position> emptyFirstPosition = new ArrayList<>();
                                    emptyFirstPosition.add(Organization.Position.EMPTY);
                                    emptyFirstPosition.addAll(organization.getPositions());
                                    emptyFirstOrganizations.add(new Organization(organization.getHomePage(), emptyFirstPosition));
                                }
                            }
                            section = new OrganizationSection(emptyFirstOrganizations);
                            break;
                    }
                    r.addSection(type, section);
                }
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", r);
        request.getRequestDispatcher(
                ("view".equals(action) ? "WEB-INF/jsp/view.jsp" : "WEB-INF/jsp/edit.jsp")
        ).forward(request, response);

        request.setAttribute("resumes", storage.getAllSorted());
        request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
    }
}