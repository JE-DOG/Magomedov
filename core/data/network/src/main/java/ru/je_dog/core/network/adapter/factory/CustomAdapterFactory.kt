package ru.je_dog.core.network.adapter.factory

import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import retrofit2.Call
import java.lang.reflect.Type
import ru.je_dog.core.network.adapter.Result

class CustomAdapterFactory: CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        val rawReturnType: Class<*> = getRawType(returnType)
        if (rawReturnType == Call::class.java){
            if (returnType is ParameterizedType) {
                val callInnerType: Type = getParameterUpperBound(0,returnType)
                if (getRawType(callInnerType) == Result::class.java){
                    return if (callInnerType is ParameterizedType){
                        val resultInnerType = getParameterUpperBound(0, callInnerType)
                        ResultCallAdapter<Any?>(resultInnerType)
                    }else {
                        ResultCallAdapter<Nothing>(Nothing::class.java)
                    }
                }
            }
        }
        return null
    }
}

private class ResultCallAdapter<R>(private val type: Type): CallAdapter<R,Call<Result<R>>> {

    override fun responseType(): Type = type

    override fun adapt(call: Call<R>): Call<Result<R>> = ResultCall(call)

}