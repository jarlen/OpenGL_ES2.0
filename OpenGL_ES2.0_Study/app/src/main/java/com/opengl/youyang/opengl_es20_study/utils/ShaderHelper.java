package com.opengl.youyang.opengl_es20_study.utils;

import android.nfc.Tag;
import android.opengl.GLES20;
import android.util.Log;

/**
 * Created by youyang on 15-4-13.
 */
public class ShaderHelper {
    private static final String TAG="ShaderHelper";

    public static int  compileVertexShader(String shaderCode){
        return compileShader(GLES20.GL_VERTEX_SHADER,shaderCode);
    }

    public static int compileFragmentShader(String shaderCode){
        return compileShader(GLES20.GL_FRAGMENT_SHADER,shaderCode);
    }

    private static int compileShader(int type ,String shaderCode){
        final int shaderObjectId=GLES20.glCreateShader(type);
        if(shaderObjectId==0){
            if(LogConfig.ON){
                Log.w(TAG,"无法创建新的shader");
            }
            return 0;
        }
        //上传和编译着色器的源码
        GLES20.glShaderSource(shaderObjectId, shaderCode);
        GLES20.glCompileShader(shaderObjectId);

        final int[] compileStatus=new int[1];
        GLES20.glGetShaderiv(shaderObjectId,GLES20.GL_COMPILE_STATUS,compileStatus,0);
        if(LogConfig.ON){
            Log.v(TAG,"编译源码的结果是："+GLES20.glGetShaderInfoLog(shaderObjectId));

        }

        if(compileStatus[0]==0){
            GLES20.glDeleteShader(shaderObjectId);
            return 0;
        }

        return shaderObjectId;
    }

    public static int linkProgram(int vertexShaderId ,int fragmentShaderId){
        final int programObjectId=GLES20.glCreateProgram();
        if(programObjectId==0){
            if(LogConfig.ON){
                Log.w(TAG,"无法创建新的程序");
            }
            return 0;
        }
        GLES20.glAttachShader(programObjectId,vertexShaderId);
        GLES20.glAttachShader(programObjectId, fragmentShaderId);

        GLES20.glLinkProgram(programObjectId);
        final int[] linkStatus =new int[1];
        GLES20.glGetProgramiv(programObjectId,GLES20.GL_LINK_STATUS,linkStatus,0);
        if(LogConfig.ON){
            Log.v(TAG,"程序连接结果是："+GLES20.glGetProgramInfoLog(programObjectId));
        }

        if(linkStatus[0]==0){
            GLES20.glDeleteProgram(programObjectId);
            if(LogConfig.ON){
                Log.w(TAG,"连接程序失败");
            }
            return 0;
        }

        return programObjectId;
    }
}