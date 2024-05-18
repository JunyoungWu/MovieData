package movieData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

i

public class ApiExplorer {
	public static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		ArrayList<MovieInfo> movList = null;
		ArrayList<MovieInfo> MovieInfoSelectList = null;

		while (true) {
			System.out.println("1.웹정보가져오기, 2.저장하기, 3.테이블읽어오기, 4.수정하기, 5.삭제하기, 6.종료");
			System.out.print("선택: ");
			int count = Integer.parseInt(sc.nextLine());
			switch (count) {
			case 1:
				System.out.println("웹정보가져오기");
				movList = webConnect();
				break;
			case 2:
				System.out.println("저장하기");
				if (movList.size() < 1) {
					System.out.println("공공데이터로부터 가져온 자료가 없습니다.");
					continue;
				}
				insertMovieInfo(movList);
				break;
			case 3:
				MovieInfoSelectList = selectMovieInfo();
				
				printMovieInfo(MovieInfoSelectList);
				System.out.println("테이블읽어오기");
				break;
			case 4:
				System.out.println("수정하기");
				int data= updateInputNodeno();
				
				if(data!=0) {
					updateMovieInfo(data);
				}
											
				break;
			case 5:
				System.out.println("삭제하기");

				deleteMovieInfo();
				break;
			case 6:
				System.out.println("종료");
				return;
			}
		}
	}

	public static int updateInputNodeno() {
	
		ArrayList<MovieInfo> ASDF = selectMovieInfo();
		printMovieInfo(ASDF);
		System.out.print("update NODENO >> ");
		int data = Integer.parseInt(sc.nextLine());
		return data;
	}

	public static void updateMovieInfo(int data) {
		String sql = "UPDATE MOVIEDB SET CURDATE = SYSDATE WHERE NODENO=?";
        Connection con = null; 
        PreparedStatement pstmt = null; 
        try {
            con = DBUtil.makeConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, data);
           
            int value = pstmt.executeUpdate();
            
            if(value == 1) {
                System.out.println("날짜 수정완료");
            }else {
                System.out.println("날짜 수정실패");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
                try {
                    if(pstmt != null) {
                        pstmt.close();
                    }
                    if(con != null) {
                        con.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
		
	}

	//버스정보 출력하기
    public static void printMovieInfo(ArrayList<MovieInfo> movieInfoSelectList) {
        if(movieInfoSelectList.size() < 1) {
            System.out.println("출력할 버스정보가 없습니다.");
            return;
        }
        for( MovieInfo data  : movieInfoSelectList) {
            System.out.println(data.toString());
        }
        
    }

	private static ArrayList<MovieInfo> selectMovieInfo() {
        ArrayList<MovieInfo> movieInfoList = null;
        String sql = "select * from MOVIEDB";
        Connection con = null; 
        PreparedStatement pstmt = null; 
        ResultSet rs = null; 
        try {
            con = DBUtil.makeConnection();
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();
            movieInfoList = new ArrayList<MovieInfo>(); 
            while(rs.next()) {
            	MovieInfo mif = new MovieInfo();
                mif.setLoadno(rs.getInt("NODENO"));
                mif.setGpslati(rs.getDouble("GPSLATI"));
                mif.setGpslong(rs.getDouble("GPSLONG"));
                mif.setLoadId(rs.getString("NODEID"));
                mif.setLoadNm(rs.getString("NODENM"));
                mif.setCurdate(rs.getString("CURDATE"));
                movieInfoList.add(bif);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
                try {
                    if(rs != null) {
                        rs.close();
                    }
                    if(pstmt != null) {
                        pstmt.close();
                    }
                    if(con != null) {
                        con.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
        return movieInfoList; 
    }

	public static int getCountMovieInfo() {
		String sql = "select count(*) as cnt from MOVIEDB";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = 0;
		try {
			con = DBUtil.makeConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			if (rs.next()) {

				count = rs.getInt("cnt");
				System.out.println("count = " + count);

			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return count;
	} // end of getSubjectTotalList

	
	
	// 공공데이터 버스 삭제하기
	private static void deleteMovieInfo() {
        int count = getCountMoiveInfo();
        if(count == 0) {
            System.out.println("버스정보내용이 없습니다.");
            return; 
        }
        System.out.println("진입1");
        String sql = "delete from MOVIEDB";
        Connection con = null; 
        PreparedStatement pstmt = null; 
        try {
            con = DBUtil.makeConnection();
            pstmt = con.prepareStatement(sql);
            System.out.println("진입2");
            int value = pstmt.executeUpdate();
            System.out.println("value : "+value);
            System.out.println("진입3");
            if(value != 0) {
                System.out.println("모든 버스정보 삭제완료");
            }else {
                System.out.println("모든 버스정보 삭제실패");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
                try {
                    if(pstmt != null) {
                        pstmt.close();
                    }
                    if(con != null) {
                        con.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
    }
	public static void insertMovieInfo(ArrayList<MovieInfo> movList) {
		if (movList.isEmpty()) {
			System.out.println("입력할 데이터가 없습니다.");
			return;
		}
		
		//저장전에 테이블 내용 삭제 
		System.out.println("진입0");
		deleteMovieInfo();
		
		for (MovieInfo data : movList) {

			Connection con = null;
			PreparedStatement pstmt = null;
			try {
				con = DBUtil.makeConnection();
			
				String sql = "INSERT INTO MOVIEDB VALUES (?,?,?,?,?,null)";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, data.getLoadno());
				pstmt.setDouble(2, data.getGpslati());
				pstmt.setDouble(3, data.getGpslong());
				pstmt.setString(4, data.getLoadId());
				pstmt.setString(5, data.getLoadNm());
				
				int value = pstmt.executeUpdate();
				
				if (value == 1) {
					System.out.println(data.getLoadno() + "정류장 등록완료");
				} else {
					System.out.println(data.getLoadno() + "정류장 등록실패");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if (pstmt != null) {
						pstmt.close();
					}
					if (con != null) {
						con.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public static ArrayList<MovieInfo> webConnect() {

		ArrayList<MovieInfo> list = new ArrayList<>();
		// 1. 요청 url 생성
		StringBuilder urlBuilder = new StringBuilder(
				"http://apis.data.go.kr/1613000/BusSttnInfoInqireService/getSttnNoList");
		try {
			urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8")
					+ "=tHGuXKnUrfjRXdJNLC%2BNJRlnd1DOMY%2B4lIapNAuiJa17%2BESaOVn38TYiW0XqpLrcDANlMhkXZl2iw%2Fdr7fftxA%3D%3D");
			urlBuilder.append("&" + URLEncoder.encode("cityCode", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8"));
			urlBuilder.append("&" + URLEncoder.encode("nodeNm", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8"));
			urlBuilder.append("&" + URLEncoder.encode("nodeNo", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8"));
			urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8"));
			urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8"));
			urlBuilder.append("&" + URLEncoder.encode("_type", "UTF-8") + "=" + URLEncoder.encode("xml", "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		// 2.서버주소 Connection con
		URL url = null;
		HttpURLConnection conn = null;
		try {
			url = new URL(urlBuilder.toString()); // 웹서버주소 action
			conn = (HttpURLConnection) url.openConnection(); // 접속요청
			conn.setRequestMethod("GET"); // get방식
			conn.setRequestProperty("Content-type", "application/json");
			System.out.println("Response code: " + conn.getResponseCode());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 3. 요청내용을 전송 및 응답처리
		BufferedReader br = null;
		try {
			// conn.getResponseCode() 서버에서 상태코드를 알려주는 값
			int statusCode = conn.getResponseCode();
			System.out.println(statusCode);
			if (statusCode >= 200 && statusCode <= 300) {
				br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			} else {
				br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
			}
			Document doc = parseXML(conn.getInputStream());
			// a. Item 태그객체 목록으로 가져온다.
			NodeList descNodes = doc.getElementsByTagName("item");

			// c. 각 item 태그의 자식태그에서 정보 가져오기
			for (int i = 0; i < descNodes.getLength(); i++) {
				// item
				Node item = descNodes.item(i);
				MovieInfo data = new MovieInfo();
				// item 자식태그에 순차적으로 접근
				for (Node node = item.getFirstChild(); node != null; node = node.getNextSibling()) {
					System.out.println(node.getNodeName() + " : " + node.getTextContent());

					switch (node.getNodeName()) {
					case "gpslati":
						data.setGpslati(Double.parseDouble(node.getTextContent()));
						break;
					case "gpslong":
						data.setGpslong(Double.parseDouble(node.getTextContent()));
						break;
					case "nodeid":
						data.setLoadId(node.getTextContent());
						break;
					case "nodenm":
						data.setLoadNm(node.getTextContent());
						break;
					case "nodeno":
						data.setLoadno(Integer.parseInt(node.getTextContent()));
						break;
					}
				}
				// d. List객체에 추가
				list.add(data);
			}
			// e.최종확인
			for (MovieInfo data : list) {
				System.out.println(data);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	public static Document parseXML(InputStream inputStream) {
		DocumentBuilderFactory objDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder objDocumentBuilder = null;
		Document doc = null;
		try {
			objDocumentBuilder = objDocumentBuilderFactory.newDocumentBuilder();
			doc = objDocumentBuilder.parse(inputStream);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) { // Simple API for XML e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return doc;
	}

}
