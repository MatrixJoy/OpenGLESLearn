project(glcore)

message(SATUS this is glcore make list)

cmake_minimum_required(VERSION 3.4.1)

set(CMAKE_C_FLAGS "${CMAKE_C_FLAGS} -Wall")
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11 -fno-rtti  -Wall")

include_directories(gles/include/)

set(GL_CORE gles/EglCore.cpp
        gles/EglSurfaceBase.cpp
        gles/OffScreenSurface.cpp
        gles/WindowSurface.cpp

        GlCoreJni.h
        GLCoreJni.cpp)

find_library(log-lib log)

add_library(glcore

        SHARED

        ${GL_CORE})

target_link_libraries(
        glcore

        EGL

        android

        ${log-lib}

        GLESv3)