package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Logout
 */
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Logout() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// セッションを取得する
		HttpSession session = request.getSession(false);

		// セッションが存在し、authの値が保存されているかチェックする
		if(session != null && session.getAttribute("auth") != null) {
			// セッションが存在し、authの値が保存されている場合
			// セッションを破棄する
			session.invalidate();
			// セッションを開始する
			session = request.getSession(true);
			// resultとmsgをサーバー側に保存する
			session.setAttribute("result", "1");
			session.setAttribute("msg", "ログアウトしました");
		} else {
			// resultとmsgをサーバー側に保存する
			session.setAttribute("result", "-1");
			session.setAttribute("msg", "既にログアウト済です");
		}
		// ログイン画面へリダイレクト
		response.sendRedirect("/AssignmentSubmissionSystem/Login");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
