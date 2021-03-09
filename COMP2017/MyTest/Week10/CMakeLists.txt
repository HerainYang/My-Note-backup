cmake_minimum_required(VERSION 3.16)
project(Week10 C)

set(CMAKE_C_STANDARD 99)

add_executable(Week10 main.c)

find_package (Threads)

target_link_libraries (Week10 ${CMAKE_THREAD_LIBS_INIT})