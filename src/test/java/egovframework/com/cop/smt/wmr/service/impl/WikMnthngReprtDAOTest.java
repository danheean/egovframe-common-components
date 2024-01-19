package egovframework.com.cop.smt.wmr.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.List;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.ContextConfiguration;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.cop.smt.wmr.service.ReportrVO;
import egovframework.com.cop.smt.wmr.service.WikMnthngReprt;
import egovframework.com.cop.smt.wmr.service.WikMnthngReprtVO;
import egovframework.com.test.EgovTestAbstractDAO;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 주간월간보고에 대한 DAO 클래스 단위 테스트
 * @author 주레피
 * @created 2023-12-12 18:04
 *
 */
@ContextConfiguration(classes = { EgovTestAbstractDAO.class, WikMnthngReprtDAOTest.class, })
@Configuration
@ImportResource({
    "classpath*:egovframework/spring/com/idgn/context-idgn-WikMnthngReprt.xml",
})
@ComponentScan(
        useDefaultFilters = false,
        basePackages = {
                "egovframework.com.cop.smt.wmr.service.impl"
        },
        includeFilters = {
                @Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes = {
                                WikMnthngReprtDAO.class
                        }
                        )
        }
        )
@NoArgsConstructor
@Slf4j
// @Commit // 주석을 없애면 테이블에 입력됨
public class WikMnthngReprtDAOTest extends EgovTestAbstractDAO{

    /**
     * idgenServiceWikMnthngReprtService
     */
    @Autowired
    @Qualifier("egovWikMnthngReprtIdGnrService")
    private EgovIdGnrService idgenServiceWikMnthngReprtService;

    /**
     * wikMnthngReprtDAO
     */
    @Autowired
    private WikMnthngReprtDAO wikMnthngReprtDAO;

    /**
     * testWeekMnthngReprt
     */
    private WikMnthngReprt testWeekReport;

    /**
     * testMonthReportReprt
     */
    private WikMnthngReprt testMonthReport;

    /**
     * testLeasderSttusVO
     */
    // private LeaderSttus testLeaderSttus;

    /**
     * default testUserVO
     */
    private LoginVO testUserVO;


    /**
     * 주간/월간보고 더미데이터 생성
     * @param reportId: 보고서ID (6자리)
     * @param reportSe: 보고서구분 (1:주간, 2:월간)
     * @param reportSj: 보고서제목
     * @param reportDe: 보고일자
     * @param writerId: 작성자ID
     * @param reporterId: 보고자ID
     * @param reportBeginDe: 보고시작일자
     * @param reportEndDe: 보고종료일자
     * @param reportCn: 보고내용
     * @param planCn: 다음보고계획
     * @param remark: 특이사항
     * @param attachFileId: 첨부파일ID
     * @param userId: 최초등록자ID, 최종수정자ID
     */
    private WikMnthngReprt makeWikMnthngReport(final String reportId, final String reportSe, final String reportSj, final String reportDe, final String writerId, final String reporterId, final String reportBeginDe, final String reportEndDe, final String reportCn, final String planCn, final String remark, final String attachFileId, final String userId) {

        final WikMnthngReprt report = new WikMnthngReprt();

        /**
         * 보고서ID
         */
        report.setReprtId(reportId);
        /**
         * 보고서구분
         */
        report.setReprtSe(reportSe);
        /**
         * 보고서제목
         */
        report.setReprtSj(reportSj);
        /**
         * 보고일자
         */
        report.setReprtDe(reportDe);
        /**
         * 작성자ID
         */
        report.setWrterId(writerId);
        /**
         * 보고자ID
         */
        report.setReportrId(reporterId);
        /**
         * 보고시작일자
         */
        report.setReprtBgnDe(reportBeginDe);
        /**
         * 보고종료일자
         */
        report.setReprtEndDe(reportEndDe);
        /**
         * 금주보고내용
         */
        report.setReprtThswikCn(reportCn);
        /**
         * 차주보고내용
         */
        report.setReprtLesseeCn(planCn);
        /**
         * 특이사항
         */
        report.setPartclrMatter(remark);
        /**
         * 첨부파일ID
         */
        report.setAtchFileId(attachFileId);
        /**
         * 최초등록자ID
         */
        report.setFrstRegisterId(userId);
        /**
         * 최종수정자ID
         */
        report.setLastUpdusrId(userId);

        return report;
    }


    /**
     * 테스트 데이터 생성
     */
    @Before
    public void testData() {
        /*
         * 테스트 간부 정보를 가져옴
         */
        testUserVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        /**
         * 월간업무보고
         */
        testMonthReport = makeWikMnthngReport(
                "WR9001",
                "2",
                "(테스트)월간보고(11월)",
                "2023-11-30",
                "USRCNFRM_00000000001",
                "USRCNFRM_00000000000",
                "2023-11-01",
                "2023-11-30",
                "(테스트)11월 월간보고 내용입니다.",
                "(테스트)12월 월간보고 계획입니다.",
                "특이사항 없습니다",
                null,
                testUserVO.getUniqId()
                );

        /**
         * 주간업무보고
         */
        testWeekReport = makeWikMnthngReport(
                "WR9002",
                "1",
                "(테스트)주간보고(12월 1주차)",
                "2023-12-11",
                "USRCNFRM_00000000001",
                "USRCNFRM_00000000000",
                "2023-12-04",
                "2023-12-08",
                "(테스트)금주 주간보고 내용입니다.",
                "(테스트)이번주 주간보고 계획입니다.",
                "특이사항 없습니다",
                null,
                testUserVO.getUniqId()
                );

        wikMnthngReprtDAO.insertWikMnthngReprt(testWeekReport);
        wikMnthngReprtDAO.insertWikMnthngReprt(testMonthReport);
    }

    /**
     * 보고자 정보 assert
     */
    private void assertSelectReport(final Object expected, final Object actual) {
        if (expected instanceof ReportrVO) {
            assertEquals(egovMessageSource.getMessage(FAIL_COMMON_SELECT), ((ReportrVO) expected).getEmplNo(), ((ReportrVO) actual).getEmplNo());
        } else if (expected instanceof WikMnthngReprtVO) {
            assertEquals(egovMessageSource.getMessage(FAIL_COMMON_SELECT), ((WikMnthngReprtVO) expected).getReprtId(), ((WikMnthngReprtVO) actual).getReprtId());
        } else {
            assertEquals(egovMessageSource.getMessage(FAIL_COMMON_SELECT), expected, actual);
        }
    }

    /**
     * 주간월간보고 정보 등록 테스트 코드
     */
    @Test
    public void testInsertWikMnthngReprt() {
        // given
        final WikMnthngReprt weekReport = makeWikMnthngReport(
                "WR9003",
                "1",
                "(테스트)주간보고(12월 2주차)",
                "2023-12-18",
                "USRCNFRM_00000000001",
                "USRCNFRM_00000000000",
                "2023-12-11",
                "2023-12-15",
                "(테스트)금주 주간보고 내용입니다.",
                "(테스트)이번주 주간보고 계획입니다.",
                "특이사항 없습니다",
                null,
                testUserVO.getUniqId()
                );
        // log.debug("reportId = {}", weekReport.getReprtId());

        // when
        final int result = wikMnthngReprtDAO.insertWikMnthngReprt(weekReport);

        // then
        assertEquals(egovMessageSource.getMessage("fail.common.insert"), 1, result);
    }

    /**
     * 주어진 조건에 맞는 보고자 조회 테스트 코드
     */
    @Test
    public void testSelectReportrList() {
        // given
        final ReportrVO reportrVO = new ReportrVO();
        reportrVO.setRecordCountPerPage(10);
        reportrVO.setFirstIndex(0);
        reportrVO.setSearchCnd("1"); // USER_NM
        reportrVO.setSearchWrd("테스트1");

        /**
         * 로그인 사용자 정보
         * ESNTL_ID(고유ID)         EMPLYR_ID    USER_NM
         * USRCNFRM_00000000000    TEST1        테스트1
         * USRCNFRM_99999999999    webmaster    웹마스터
         */

        // when
        final List<ReportrVO> resultList = wikMnthngReprtDAO.selectReportrList(reportrVO);

        for (final ReportrVO result : resultList) {
            if (log.isDebugEnabled()) {
                log.debug("result={}", result);
                log.debug("UserNm={}, {}", reportrVO.getSearchWrd(), result.getEmplyrNm());
            }

            // then
            assertSelectReport(reportrVO.getSearchWrd(), result.getEmplyrNm());
        }
    }

    /**
     * 보고자 목록 전체 개수 조회 테스트 코드
     */
    @Test
    public void testSelectReportrListCnt() {
        // given
        final ReportrVO reportrVO = new ReportrVO();
        reportrVO.setSearchCnd("1");
        reportrVO.setSearchWrd("테스트1");

        // when
        final int resultAll = wikMnthngReprtDAO.selectReportrListCnt(new ReportrVO());
        final int result = wikMnthngReprtDAO.selectReportrListCnt(reportrVO);

        // then
        assertTrue(egovMessageSource.getMessage(FAIL_COMMON_SELECT), resultAll > 0);
        assertEquals(egovMessageSource.getMessage(FAIL_COMMON_SELECT), 1, result);
    }

    /**
     * 주어진 조건에 맞는 직위명 조회 테스트 코드
     */
    @Test
    public void testSelectWrterClsfNm() {
        // given
        final String writerId = testUserVO.getUniqId(); // 관리자
        /**
         * 로그인 사용자 정보
         * ESNTL_ID(고유ID)         EMPLYR_ID    USER_NM    OFCPS_NM
         * USRCNFRM_00000000000    TEST1        테스트1     관리자
         * USRCNFRM_99999999999    webmaster    웹마스터     웹관리자
         */

        // when
        final String result = wikMnthngReprtDAO.selectWrterClsfNm(writerId);

        // then
        assertSelectReport("관리자", result);
    }

    /**
     * 주어진 조건에 맞는 주간월간보고 목록 조회 테스트 코드
     */
    @Test
    public void testSelectWikMnthngReprtList() {
        // given
        final WikMnthngReprtVO weekMonthReportVO = new WikMnthngReprtVO();
        weekMonthReportVO.setRecordCountPerPage(10);
        weekMonthReportVO.setFirstIndex(0);
        // 2023년 전체 보고 목록을 가져 온다.
        weekMonthReportVO.setSearchDe("0");
        weekMonthReportVO.setSearchBgnDe("2023-01-01");
        weekMonthReportVO.setSearchEndDe("2023-12-31");
        // 테스트 보고의 보고자 정보를 검색ID에 전달
        weekMonthReportVO.setSearchId(testWeekReport.getReportrId());
        // 제목으로 조회
        weekMonthReportVO.setSearchCnd("0");
        weekMonthReportVO.setSearchWrd(testWeekReport.getReprtSj());
        // 미승인 조회
        weekMonthReportVO.setSearchSttus("0");
        // 주간보고 조회
        weekMonthReportVO.setSearchSe("1");

        // when
        final List<WikMnthngReprtVO> resultList = wikMnthngReprtDAO.selectWikMnthngReprtList(weekMonthReportVO);

        for (final WikMnthngReprtVO result : resultList) {
            if (log.isDebugEnabled()) {
                log.debug("result={}", result);
                log.debug("reportId={}, {}", testWeekReport.getReprtId(), result.getReprtId());
            }

            // then
            assertSelectReport(testWeekReport.getReprtId(), result.getReprtId());
        }
    }

    /**
     * 주어진 조건에 맞는 주간월간보고 전체 개수 조회 테스트 코드
     */
    @Test
    public void testSelectWikMnthngReprtListCnt() {
        // given
        final WikMnthngReprtVO weekMonthReportVO = new WikMnthngReprtVO();
        // 2023년 전체 보고 목록을 가져 온다.
        weekMonthReportVO.setSearchDe("0");
        weekMonthReportVO.setSearchBgnDe("2023-01-01");
        weekMonthReportVO.setSearchEndDe("2023-12-31");
        // 테스트 보고의 보고자 정보를 검색ID에 전달
        weekMonthReportVO.setSearchId(testMonthReport.getReportrId());
        // 제목으로 조회
        weekMonthReportVO.setSearchCnd("0");
        weekMonthReportVO.setSearchWrd(testMonthReport.getReprtSj());
        // 미승인 조회
        weekMonthReportVO.setSearchSttus("0");
        // 월간보고 조회
        weekMonthReportVO.setSearchSe(testMonthReport.getReprtSe());

        // when
        final Integer result = wikMnthngReprtDAO.selectWikMnthngReprtListCnt(weekMonthReportVO);

        // then
        assertSelectReport(1, result);
    }

    /**
     * 주어진 조건에 맞는 주간월간보고 조회 테스트 코드
     */
    @Test
    public void testSelectWikMnthngReprt() {
        // given
        final WikMnthngReprtVO weekMonthReportVO = new WikMnthngReprtVO();
        weekMonthReportVO.setReprtId(testMonthReport.getReprtId());

        // when
        final WikMnthngReprtVO result = wikMnthngReprtDAO.selectWikMnthngReprt(weekMonthReportVO);

        // then
        assertSelectReport(weekMonthReportVO, result);
    }

    /**
     * 주간월간보고 수정 테스트 코드
     */
    @Test
    public void testUpdateWikMnthngReprt() {
        // given
        testWeekReport.setReprtSj(testWeekReport.getReprtSj() + " - (수정)");
        final WikMnthngReprt weekMonthReport = testWeekReport;

        // when
        final int result = wikMnthngReprtDAO.updateWikMnthngReprt(weekMonthReport);

        // then
        assertEquals(egovMessageSource.getMessage("fail.common.update"), 1, result);
    }

    /**
     * 주간월간보고 삭제 테스트 코드
     */
    @Test
    public void testDeleteWikMnthngReprt() {
        // given
        final WikMnthngReprt weekMonthReport = new WikMnthngReprt();
        weekMonthReport.setReprtId(testMonthReport.getReprtId());

        // when
        final int result = wikMnthngReprtDAO.deleteWikMnthngReprt(weekMonthReport);

        // then
        assertEquals(egovMessageSource.getMessage("fail.common.delete"), 1, result);
    }

}

