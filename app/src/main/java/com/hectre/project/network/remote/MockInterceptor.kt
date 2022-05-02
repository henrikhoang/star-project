package com.hectre.project.network.remote

import okhttp3.*

class MockInterceptor : Interceptor {
    private val mockResponse = """
        {
"pruning": [
    {
      "name": "Ben",
      "rows": [
        {
          "rowNo": 1,
          "treesWorked": 0,
          "total": 320,
          "coWorker": "Yan"
        },
        {
          "rowNo": 2,
          "treesWorked": 50,
          "total": 300,
          "coWorker": "Chris"
        },
        {
          "rowNo": 3,
          "treesWorked": 30,
          "total": 150,
          "coWorker": "Hailey"
        }
      ]
    },
    {
      "name": "Chris W",
      "rows": [
        {
          "rowNo": 1,
          "treesWorked": 10,
          "total": 320,
          "coWorker": "Dan"
        },
        {
          "rowNo": 2,
          "treesWorked": 50,
          "total": 300,
          "coWorker": "James"
        }
      ]
    }
  ],
  "thinning": [
    {
      "name": "Daniel W",
      "rows": [
        {
          "rowNo": 1,
          "treesWorked": 7,
          "total": 320,
          "coWorker": "Danny"
        },
        {
          "rowNo": 2,
          "treesWorked": 56,
          "total": 400,
          "coWorker": "Tom"
        }
      ]
    }
  ]
}
    """

    override fun intercept(chain: Interceptor.Chain): Response {
        val responseString = mockResponse
        return chain.proceed(chain.request())
            .newBuilder()
            .code(200)
            .protocol(Protocol.HTTP_2)
            .message(responseString)
            .body(
                ResponseBody.create(
                    MediaType.parse("application/json"),
                    responseString.toByteArray()
                )
            )
            .addHeader("content-type", "application/json")
            .build()
    }
}