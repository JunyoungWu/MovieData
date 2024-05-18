package movieData;

import java.util.Date;

public class MovieInfo {
	String movieCd;		 // 영화코드
	String movieNm; 	 // 영화제목
	String movieNmEn; 	 // 영어제목
	int prdtYear; 		 // 제작년도
	Date openDt;		 // 개봉일
	String nationAlt; 	 // 나라
	String genreAlt; 	 // 장르
	String director; 	 // 감독명
	String companyNm; 	 // 제작사
	public MovieInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public MovieInfo(String movieCd, String movieNm, String movieNmEn, int prdtYear, Date openDt, String nationAlt,
			String genreAlt, String director, String companyNm) {
		super();
		this.movieCd = movieCd;
		this.movieNm = movieNm;
		this.movieNmEn = movieNmEn;
		this.prdtYear = prdtYear;
		this.openDt = openDt;
		this.nationAlt = nationAlt;
		this.genreAlt = genreAlt;
		this.director = director;
		this.companyNm = companyNm;
	}
	public String getMovieCd() {
		return movieCd;
	}
	public void setMovieCd(String movieCd) {
		this.movieCd = movieCd;
	}
	public String getMovieNm() {
		return movieNm;
	}
	public void setMovieNm(String movieNm) {
		this.movieNm = movieNm;
	}
	public String getMovieNmEn() {
		return movieNmEn;
	}
	public void setMovieNmEn(String movieNmEn) {
		this.movieNmEn = movieNmEn;
	}
	public int getPrdtYear() {
		return prdtYear;
	}
	public void setPrdtYear(int prdtYear) {
		this.prdtYear = prdtYear;
	}
	public Date getOpenDt() {
		return openDt;
	}
	public void setOpenDt(Date openDt) {
		this.openDt = openDt;
	}
	public String getNationAlt() {
		return nationAlt;
	}
	public void setNationAlt(String nationAlt) {
		this.nationAlt = nationAlt;
	}
	public String getGenreAlt() {
		return genreAlt;
	}
	public void setGenreAlt(String genreAlt) {
		this.genreAlt = genreAlt;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public String getCompanyNm() {
		return companyNm;
	}
	public void setCompanyNm(String companyNm) {
		this.companyNm = companyNm;
	}
	@Override
	public String toString() {
		return "[movieCd=" + movieCd + ", movieNm=" + movieNm + ", movieNmEn=" + movieNmEn + ", prdtYear="
				+ prdtYear + ", openDt=" + openDt + ", nationAlt=" + nationAlt + ", genreAlt=" + genreAlt
				+ ", director=" + director + ", companyNm=" + companyNm + "]";
	}
	
	
	
	
	
}
