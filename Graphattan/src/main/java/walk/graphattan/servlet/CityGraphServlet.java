package walk.graphattan.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import walk.graphattan.builder.midtown.CornerDesc;
import walk.graphattan.builder.midtown.MidtownGraphBuilder;
import walk.graphattan.converter.CityGraphConverter;
import walk.graphattan.dto.SimpleCityGraphDTO;
import walk.graphattan.graph.CityGraph;

@WebServlet("/graph")
public class CityGraphServlet extends HttpServlet {

	private static final long serialVersionUID = -1998267727045956279L;
	
	private ObjectMapper om = new ObjectMapper();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		MidtownGraphBuilder builder = new MidtownGraphBuilder();
		boolean valid_input = true;
		
		// collect start and finish specs as request parameters
		try {
			int start_st = Integer.parseInt(req.getParameter("start_st"));
			int start_ave = Integer.parseInt(req.getParameter("start_ave"));
			CornerDesc start_corner = CornerDesc.valueOf(req.getParameter("start_corner"));
			int finish_st = Integer.parseInt(req.getParameter("finish_st"));
			int finish_ave = Integer.parseInt(req.getParameter("finish_ave"));
			CornerDesc finish_corner = CornerDesc.valueOf(req.getParameter("finish_corner"));
			builder.setStart(start_st, start_ave, start_corner);
			builder.setFinish(finish_st, finish_ave, finish_corner);
		} catch (Exception e) {
			valid_input = false;
		}
		
		if (valid_input) {
			// build graph
			CityGraph cityGraph = builder.build();
			SimpleCityGraphDTO simpleCityGraph = CityGraphConverter.convertCityGraphToSimpleDTO(cityGraph);
			// convert graph to JSON
			String graphJSON = CityGraphConverter.getJSON(simpleCityGraph);
			// package into response and send
			resp.getWriter().write(graphJSON);
		} else {
			resp.sendError(400, "Invalid input");
		}
		
	}

}
