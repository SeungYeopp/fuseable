INSERT INTO user_master (ACCOUNT_EMAIL, ACCOUNT_ID, ACCOUNT_NICKNAME, CREATE_TIME, ACCOUNT_PROFILE_IMG, USER_ROLE) values
('wlwlwlwllw@naver.com', '2425965818', '방지훈', now(), 'http://k.kakaocdn.net/dn/bDj8P1/btrhcObQN1R/W8kGwDIlb6RMUyX7m0r6PK/img_640x640.jpg', 'ROLE_USER'),
('ku9907@naver.com', '2424347608', '구완모', now(),  'http://k.kakaocdn.net/dn/bnSMsh/btq3haeHGwv/6hL8F5knn3fHHqtLDloRK0/img_640x640.jpg', 'ROLE_USER'),
('syt05342@naver.com', '2425946019', '강승엽', now(), 'http://k.kakaocdn.net/dn/1OKeL/btrN9kPD3d1/UzezB2ufp7tZjedJ9OHf7k/img_640x640.jpg', 'ROLE_ADMIN');

INSERT INTO project (TITLE) values
('{"title":"예비캡스톤 설계"}'),
('{"title":"전자회로실험2"}'),
('{"title":"Spring STUDY"}');

INSERT INTO project_user_mapping (PROJECT_BOOKMARK, ROLE, PROJECT_ID, USER_ID) values
(TRUE, 'ROLE_ADMIN', 1, 2),
(TRUE, 'ROLE_ADMIN', 2, 2),
(FALSE, 'ROLE_ADMIN', 3, 2),
(TRUE, 'ROLE_USER', 1, 1),
(TRUE, 'ROLE_USER', 1, 3);

INSERT INTO schedule (CHECK_BOX, USER_ID) values
('1100000000000000010000000000000000000011100000000000000000000000100000', 1),
('1100000000000000010000000000010000000000000000010100000000000000100000', 2),
('0001000000100000010000100000100000000010010000001000000000000001000000', 3);

INSERT INTO note (CREATED_DATE, MODIFIED_DATE, ARRAY_ID, CONTENT, END_DATE, NEXT, PREVIOUS, START_DATE, STEP, TITLE, WRITER_ID, PROJECT_ID, USER_ID) values
(now(), now(), 0, 'Front-End Design', '2023-06-02', 2, 0, now(), 'TODO', '프론트 디자인', 3, 1, 2),
(now(), now(), 1, '최종 보고서 작성', '2022-12-01', 3, 1, now(), 'PROGRESS', '최종 보고서 작성', 3, 1, 3),
(now(), now(), 2, '추가 기능 생성', '2022-03-31', 4, 2, now(), 'TODO', '북마크 생성', 3, 1, 3),
(now(), now(), 3, 'D&D Error 처리', '2022-12-02', 5, 3, now(), 'VERIFY', 'Drag and Drop', 2, 1, 2),
(now(), now(), 4, 'Kakao Login 완성', '2022-10-31', 6, 4, now(), 'DONE', 'Kakao Login', 3, 1, 3),
(now(), now(), 0, '납땜을 통한 모듈 생성', '2022-10-31', 0, 0, now(), 'DONE', '납땜', 3, 2, 3),
(now(), now(), 5, '최종발표자료 만들기', '2022-12-01', 7, 5, now(), 'PROGRESS', '최종발표자료', 3, 1, 3),
(now(), now(), 6, '예비캡스톤 필요 기술스택 학습', '2022-09-30', 8, 6, now(), 'DONE', '기술 스택', 3, 1, 1),
(now(), now(), 7, '최종 구현 domain들을 바탕으로 ERD 수정', '2022-10-29', 9, 7, now(), 'DONE', 'ERD 수정', 3, 1, 3),
(now(), now(), 8, '프로젝트 Test에 필요한 Dummy Data 추가', '2022-11-30', 10, 8, now(), 'DONE', 'Dummy Data 추가', 3, 1, 3),
(now(), now(), 9, '제안서 작성', '2022-09-23', 11, 9, now(), 'DONE', '제안서 작성', 3, 1, 1),
(now(), now(), 10, 'Front-Back 통신 에러처리', '2022-12-02', 0, 10, now(), 'VERIFY', '에러 처리', 3, 1, 2)
;


INSERT INTO article(BOOKMARK, CONTENT, TITLE, USER_ID, PROJECT_ID, CREATE_DATE) values
(0, '12월 1일 (목) 23:59 KLAS 업로드' || CHR(13) || CHR(10) || '- 과제최종보고서, 발표자료 제출' || CHR(13) || CHR(10) ||
 '(최종보고서 완성도를 높이기 위한 경우 12월 5일까지 추가기한에 제출도 가능)', '최종보고서 제출', 3, 1, '2023-05-15'),
(0, '12월 1일 (목) 23:59 KLAS 업로드' || CHR(13) || CHR(10) || '- 주간업무보고서 제출', '주간업무 보고서', 3, 1, '2023-05-20'),
(0, '12월 2일 (금) 오후 5시' || CHR(13) || CHR(10) || '최종발표시연 영상 : 웹디스크 업로드'  || CHR(13) || CHR(10) ||
 '발표+시연영상 각각 10분으로 최대 15분 범위 내에서 하나의 파일로 구성', '최종발표시연 영상', 2, 1, '2023-05-16'),
(0, '12월 4일 (일) 정오' || CHR(13) || CHR(10) || '발표 영상 공개' , '최종 발표 영상 공개', 1, 1, '2023-05-17'),
(0, '12월 5일 (월) 자정' || CHR(13) || CHR(10) || '동료평가 KLAS 업로드' , '동료평가 KLAS 업로드', 2, 1, '2022-11-30'),
(0, '12월 7일 (수) 자정' || CHR(13) || CHR(10) || '조장이 우수한 동료평가 feedback 선택한 결과 KLAS 업로드', '우수 동료평가 Feedback', 3, 1, '2022-11-30')
;