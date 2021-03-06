package vn.myclass.web.controller.admin;

import vn.myclass.core.dto.ListenGuidelineDTO;
import vn.myclass.core.service.ListenGuidelineService;
import vn.myclass.core.service.impl.ListenGuidelineServiceImpl;
import vn.myclass.core.web.common.WebConstants;
import vn.myclass.core.web.utils.RequestUtil;
import vn.myclass.web.controller.command.ListenGuidelineCommand;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/admin-guideline-listen-list.html")
public class ListenGuidelineController extends HttpServlet {
    private ListenGuidelineService listenGuidelineService = new ListenGuidelineServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        ListenGuidelineCommand command = new ListenGuidelineCommand();
//        command.setMaxPageItems(2);
//        RequestUtil.initSearchBean(req,command);
//        Object[] objects = listenGuidelineService.findListenGuidelineByProperties(null,
//                null,
//                command.getSortExpression(),
//                command.getSortDirection(),
//                command.getFirstItem(),
//                command.getMaxPageItems());
//        command.setListResult((List<ListenGuidelineDTO>) objects[1]);
//        command.setTotalItems(Integer.parseInt(objects[0].toString()) );
//        req.setAttribute(WebConstants.LIST_ITEMS,command);
        RequestDispatcher rd = req.getRequestDispatcher("/view/admin/listenguideline/list.jsp");
        rd.forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
