<#import "/spring.ftl" as spring />

<!DOCTYPE HTML>
<html>
    <head>
      <title>Latest</title>
      <link rel="stylesheet"
            href="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/styles/default.min.css">
      <script src="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/highlight.min.js"></script>
      <script>hljs.initHighlightingOnLoad();</script>
      <style>
          pre {
            width: 40vw;
          }

      </style>
      <meta charset="UTF-8">
    </head>
    <body>
      <h2 class="greetings">Latest code snippets</h2>
      <div>
      <#list codeList as snippets>
        <p><span class="time">${snippets.date}</span></p>
      </div>
      <div>
        <form id="codeForm">
          <pre id="code_snippet"><code>${snippets.code}</code></pre>
        </form>
        </#list>
      </div>
    </body>
</html>