# electronic-shopping-mall
전자상가는 전자 상품 구매 서비스를 위한 API 서버입니다.   
클라이언트는 카카오 오븐을 사용해서 화면을 구성했고 이를 바탕으로 각 기능을 정의했습니다.([전자상가 화면](https://ovenapp.io/view/CqS6iGTkM4NpGOPQcfrqcQG6uQRftAbN/))   

### CI
젠킨스를 사용해서 테스트, 빌드를 자동화했습니다.   
젠킨스는 깃허브 웹훅을 받아서 CI를 진행합니다.
   
![플로우차트](https://user-images.githubusercontent.com/25922366/94126709-e8a48d80-fe92-11ea-8f7d-c4488b54e827.png)

### HTTP 상태 코드
클라이언트가 보내는 http 요청에 대해 적절한 응답을 보낼 수 있도록 문서를 참고했습니다.   
참고 문서 : https://developer.mozilla.org/ko/docs/Web/HTTP/Status

### URI 설계
URI 설계 시 일관적인 명명 규칙을 적용하여 API 직관적으로 유지할 수 있도록 했습니다.   
참고 문서 : https://docs.microsoft.com/ko-kr/azure/architecture/best-practices/api-design

### Git 브랜치 전략
전자상가 프로젝트는 Git Flow 전략으로 브랜치를 관리합니다.   
이슈에 대응하여 브랜치를 생성하고 작업한 내용은 Pull Request를 보내 리뷰를 진행한 후 merge를 진행합니다.   

![git flow](https://user-images.githubusercontent.com/25922366/80060110-af28c880-8568-11ea-9bd8-af4031908342.jpg)

|브랜치|설명|
|------|---|
|master|제품으로 출시될 수 있는 브랜치|
|develop|다음 출시 버전을 개발하는 브랜치|
|feature|기능을 개발하는 브랜치|
|release|이번 출시 버전을 준비하는 브랜치|
|hotfix|출시 버전에서 발생한 버그를 수정하는 브랜치|

브랜치 전략 참고 : [우아한형제들 기술 블로그](https://woowabros.github.io/experience/2017/10/30/baemin-mobile-git-branch-strategy.html)

### 사용 기술 및 환경
Spring boot, Gradle, JPA, MySQL, Java8, Jenkins   

### ERD
![image](https://user-images.githubusercontent.com/25922366/94115073-306fe880-fe84-11ea-8b20-117e1ccd973d.png)
