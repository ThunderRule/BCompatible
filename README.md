# Runtime permission request lib
Of course,like OkHttp

- There is Request
- There is Response
- There is Interceptor

## Use:
```kotlin
//Initialization
val okPemission = OkPermission.Builder()
    .addPermission(Manifest.permission.CAMERA)
    .addPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    .addInterceptor(DialogInterceptor())
    .build()
//Start request
okPermission.start(context = this){ response ->
    //result
}
```

## About Request:
There is permission list,Context object.

## About Response:
According to whether the status is equal to the number of permissions, it can be judged whether the returned request result is normal.
There is a HashMap that contains the name of the permission and whether it is authorized or not.
Granted permission, denied permission, no longer ask permission, all have a list cache.

## About Interceptor:
Like OkHttp, but a bit different from OkHttp.
The chain did not return, but there was a callback.

### Use:
```kotlin
class ParamsInterceptor : Interceptor {
    override fun interceptor(chain: Interceptor.Chain, callback: GoBack) {
        val request = chain.request()
        val permissions = request.getPermissions()
        //Processing request
        chain.proceed(request){response ->
            //Processing response
            
            //must, keep the chain of responsibility running
            callback.onPermissionBack(response)
        }
    }
}
```