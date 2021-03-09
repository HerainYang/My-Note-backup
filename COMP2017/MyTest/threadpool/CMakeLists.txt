cmake_minimum_required(VERSION 3.16)
project(threadpool C)

set(CMAKE_C_STANDARD 99)

add_executable(threadpool main.c threadpool.h threadpool.c)

find_package (Threads)

target_link_libraries (threadpool ${CMAKE_THREAD_LIBS_INIT})