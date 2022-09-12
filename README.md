# 주류 추천 서비스 술고래🍷

### 개요
'술' 에 대한 정보 제공 및 추천

### 개발 환경
- 개발 언어 : Java, Python
- 개발 tool : Android Studio, Juypter Notebook
- Database : Firebase
- 라이브러리 : Numpy, Pandas, SciPy, Scikit-Learn, 

## 추천 시스템🥂
### 데이터 셋
- 인스타그램 및 주류 수입사, 더술닷컴 크롤링을 통해 주류 데이터 수집
![데이터셋술](https://user-images.githubusercontent.com/70012637/189680663-ea059b65-57d8-4236-ab05-dc07acdef778.png)
- class 대분류 : wine / makgeoli / beer / vodka / soju / whisky / korean (전통주)
- category 중분류 : 와인-레드 / 와인-화이트 / 위스키-American / 위스키-Irish / 맥주-IPA / 맥주-라거 등 24가지

### 사용 모델
#### Euclidean Distance
- 도수에 대한 유사도
#### Cosine Similarity
- 풍미 및 기타 정보의 유사도
- bag of words 를 통해 문장을 단어의 빈도수로 나타낸 Matrix를 사용하여 코사인 유사도를 구함
#### 두 모델의 가중치를 1:1로 둔, 최종 Similarity를 구현

## 서비스 구성💻
![시연영상🎞](https://user-images.githubusercontent.com/70012637/189685342-a2797378-dc0b-482c-bdf2-239f71348f40.mp4)
|<img src="https://user-images.githubusercontent.com/70012637/189680644-46a14b2b-e2c0-456d-a912-f5a326ad0d48.png">|<img src="https://user-images.githubusercontent.com/70012637/189680649-a7b5fc9c-0473-4ce8-9732-c7c0cc5c13bb.png">|
|---|---|
<br>

|주류 검색|주류 상세보기|즐겨찾기|
|---|---|---|
|1. 술 이름 및 주종 대분류, 중분류를 통한 검색 기능|1. 주종에 따른 커뮤니티 구성<br>2. 전체/기타 게시판<br>3. 글쓰기|1. 주종 및 취향에 따른 사용자의 즐겨찾기 기능|
|:**로그인, 회원가입**:|:**내정보 상세보기**:|
|1. 로그인<br>2. 회원가입<br>3. 아이디/비밀번호 찾기<br>4. 구글 간편 로그인|1. 프로필 이미지 설정<br>2. 로그아웃<br>3. 회원탈퇴|

## 팀원 소개👨‍👨‍👧‍👧
- 민지웅🙋‍♂️
  : 데이터 수집 및 전처리, 추천 서비스 모델링 <br>
- 안유진👧
  : 추천 서비스 모델링 및 로그인, 프로필, 즐겨찾기 기능 구현 <br>
- 김동현👨‍💻
  : 게시판, 상세보기, 검색 기능 구현 <br>
