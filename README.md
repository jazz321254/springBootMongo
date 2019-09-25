# 使用 Spring Boot 練習 MongoDB

這個Repository是一個簡單的mongoDB練習，使用簡單的資料結構，利用Spring Data快速取得數據，也使用Aggregation的pipeline來獲取較複雜的資訊。

## 安裝

你的系統需要安裝Docker即可執行，以下是在Mac下簡單安裝Docker的方式。

首先是先安裝[Homebrew](https://brew.sh/index_zh-tw)
再安裝 Homebrew-Cask

```bash
brew tap caskroom/cask
```

接著用cask安裝Docker

```bash
brew cask install docker
```

## 使用  

只需執行一個指令即可運行本程式碼

```bash
docker-compose up
```

之後就可以連連看API是否正常運行

```url
http://127.0.0.1:8080/api/v1/subjects
```

其他API的URI可以參考程式ApiController這支程式

## License
[MIT](https://choosealicense.com/licenses/mit/)