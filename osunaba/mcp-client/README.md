mcp-client
========================

[Spring AI - MCP Starter Client](https://github.com/spring-projects/spring-ai-examples/blob/e0ba3438baf504e93fdb8136324d17b757af68de/model-context-protocol/client-starter/starter-default-client/README.md
) を参考に作成

## 使い方

### ビルド

```bash
(cd ../../ && ./gradlew :osunaba:mcp-client:bootJar && ./gradlew :osunaba:mcp-server:bootJar)
```

### Gemini API

```bash
java -Dai.user.input='NYの天気を教えて' -Dspring.profiles.active=gemini \
  -Dspring.ai.openai.api-key=XXXX \
  -Dspring.ai.mcp.client.stdio.connections.server1.args=-jar,../mcp-server/build/libs/mcp-server.jar -jar build/libs/mcp-client.jar
```

```md
>>> QUESTION: NYの天気を教えて

>>> ASSISTANT: はい、ニューヨーク州の天気についてですね。
まず、ニューヨーク州内の気象警報を `spring_ai_mcp_client_server1_getAlerts` ツールで確認しました。現在、いくつかの警報や注意報が出ています。

,**主な気象警報・注意報（ニューヨーク州内）:**

,*   **冬季気象注意報 (Winter Weather Advisory):**
    ,*   Orange郡、Putnam郡: 今朝10時まで、主に標高の高い地域で最大2インチの積雪。路面凍結の可能性。
    ,*   Delaware郡、Sullivan郡: 今日の午後2時まで、湿った雪による2〜4インチ（標高2000フィート以上では最大5インチ）の積雪。路面凍結の可能性。
    ,*   Schoharie郡、Albany郡西部、Rensselaer郡東部など、複数の郡: 今日の午後2時まで、湿った雪による2〜5インチ（標高1500フィート以上ではさらに多くなる可能性）の積雪。路面凍結の可能性。
,*   **濃霧注意報 (Dense Fog Advisory):**
    ,*   Niagara郡、Orleans郡、Monroe郡、Wayne郡、Erie郡北部/南部など、ニューヨーク州西部: 今朝11時まで、視界が4分の1マイル以下になる濃霧。運転注意。
,*   **沿岸洪水注意報 (Coastal Flood Statement):**
    ,*   Nassau郡南部、Richmond (Staten Is.)郡、Suffolk郡南西部など: 今朝および今晩、沿岸部や海岸線近くの低い場所で軽微な浸水の可能性。
,*   **特別気象声明 (Special Weather Statement):**
    ,*   北部の一部地域: 早朝にかけて、雨、雪、みぞれの混合。1インチ未満の積雪。道路や歩道がぬかるむ可能性。
    ,*   Steuben郡: 局地的に濃い霧が発生し、視界が悪くなる可能性。
    ,*   その他の一部地域: 湿った雪による視界不良や路面のぬかるみ。

これらの警報が出ている地域では、特に運転などに注意が必要です。

ニューヨーク州は広いため、もし特定の都市（例: ニューヨーク市、バッファロー、オールバニなど）の天気予報（気温、天気など）が必要でしたら、場所を指定していただければ `spring_ai_mcp_client_server1_getWeatherForecastByLocation` ツールを使って詳細な情報をお調べします。どの地域の情報をご希望ですか？
```

### Anthropic

```bash
java -Dai.user.input='NYの天気を教えて' -Dspring.ai.model.chat=anthropic\
   -Dspring.ai.anthropic.api-key=XXXX \
   -Dspring.ai.mcp.client.stdio.connections.server1.args=-jar,../mcp-server/build/libs/mcp-server.jar -jar build/libs/mcp-client.jar
```

```md
>>> QUESTION: NYの天気を教えて

>>> ASSISTANT: # ニューヨーク州の天気情報

ニューヨーク州の現在の気象警報をお知らせします。いくつかの地域で注意が必要な状況となっています。

## 主な警報内容

### 冬の天候に関する警報
- **デラウェア郡・サリバン郡**: 湿った雪が予想され、積雪量は2〜4インチ、標高2000フィート以上の地域では最大5インチに達する可能性があります。午後2時まで有効。
- **オレンジ郡・パトナム郡**: 追加の積雪が最大2インチ、特に高地で予想されます。午前10時まで有効。

### 濃霧に関する警報
- **ナイアガラ、オーリンズ、モンロー**など西部地域: 視界が1/4マイル程度まで低下する濃霧。午前11時まで有効。

### 沿岸洪水に関する注意報
- **南ナッソー郡**、**サフォーク郡南西部**、**リッチモンド(スタテン島)**などの沿岸地域: 水際や海岸線付近の脆弱な地域で、地面から最大0.5フィートの浸水が予想されています。

### 特別気象情報
- 北部地域: 雨、雪、氷雨の混合が早朝まで予想されます。視界が一時的に悪化する可能性があります。
- ステューベン地域: 局所的に濃い霧が発生し、視界が1マイル未満に低下する可能性があります。

## 注意事項
```

### Vertex AI Gemini

```bash
java -Dai.user.input='NYの天気を教えて' -Dspring.profiles.active=vertex-ai-gemini\
  -Dspring.ai.vertex.ai.gemini.projectId=$(gcloud config get-value project)\
  -Dspring.ai.mcp.client.stdio.connections.server1.args=-jar,../mcp-server/build/libs/mcp-server.jar -jar build/libs/mcp-client.jar
```

```
>>> QUESTION: NYの天気を教えて

>>> ASSISTANT: はい、ニューヨークの天気ですね。APIを使って調べてみます。ニューヨーク市の天気予報と、ニューヨーク州の気象警報を確認しますね。少々お待ちください。
```

`NYの天気を教えて。前置きはせず結果だけ教えて。` と言うとツールを使ってくれるが、 https://github.com/spppring-projects/spring-ai/issues/2647 によりエラーになる。
