# 3차 프로젝트

- 각 서버에 있는 maven-wrapper.properties 파일
```
로컬에 Maven이 설치되어 있지 않아도 프로젝트에서 지정한 버전의 Maven을 자동으로 다운로드하고 사용할 수 있게 함.

이러면 모든 개발자가 한 가지 버전의 maven을 사용시킴.

->자세한 설명은 trs의 maven-wrapper.properties 파일 참조
```

- db config
```
Spring Data JPA가 자동으로 리포지토리를 활성화하고 관리할 수 있도록 설정
서버간 데이터베이스를 연동 할 수 있도록 설정
```

- trs 서버 추가하면서 한 일
1. db 설정용 TrsConfig 추가 (stg,mfr,trs에 추가)
2. application.yml에 db 경로 설정 (stg,mfr,trs에 추가)
3. gateway에 routes 추가