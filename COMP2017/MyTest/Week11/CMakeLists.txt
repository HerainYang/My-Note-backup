cmake_minimum_required(VERSION 3.16)
project(Week11 C)

set(CMAKE_C_STANDARD 99)

add_executable(Week11 main.c)

find_package (Threads)

target_link_libraries (Week11 ${CMAKE_THREAD_LIBS_INIT})