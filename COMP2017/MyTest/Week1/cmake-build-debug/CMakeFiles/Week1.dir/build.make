# CMAKE generated file: DO NOT EDIT!
# Generated by "Unix Makefiles" Generator, CMake Version 3.16

# Delete rule output on recipe failure.
.DELETE_ON_ERROR:


#=============================================================================
# Special targets provided by cmake.

# Disable implicit rules so canonical targets will work.
.SUFFIXES:


# Remove some rules from gmake that .SUFFIXES does not remove.
SUFFIXES =

.SUFFIXES: .hpux_make_needs_suffix_list


# Suppress display of executed commands.
$(VERBOSE).SILENT:


# A target that is always out of date.
cmake_force:

.PHONY : cmake_force

#=============================================================================
# Set environment variables for the build.

# The shell in which to execute make rules.
SHELL = /bin/sh

# The CMake executable.
CMAKE_COMMAND = /home/herain/JetBrains/clion-2020.1.2/bin/cmake/linux/bin/cmake

# The command to remove a file.
RM = /home/herain/JetBrains/clion-2020.1.2/bin/cmake/linux/bin/cmake -E remove -f

# Escaping for special characters.
EQUALS = =

# The top-level source directory on which CMake was run.
CMAKE_SOURCE_DIR = /home/herain/Documents/COMP2017/MyTest/Week1

# The top-level build directory on which CMake was run.
CMAKE_BINARY_DIR = /home/herain/Documents/COMP2017/MyTest/Week1/cmake-build-debug

# Include any dependencies generated for this target.
include CMakeFiles/Week1.dir/depend.make

# Include the progress variables for this target.
include CMakeFiles/Week1.dir/progress.make

# Include the compile flags for this target's objects.
include CMakeFiles/Week1.dir/flags.make

CMakeFiles/Week1.dir/main.c.o: CMakeFiles/Week1.dir/flags.make
CMakeFiles/Week1.dir/main.c.o: ../main.c
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/home/herain/Documents/COMP2017/MyTest/Week1/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_1) "Building C object CMakeFiles/Week1.dir/main.c.o"
	/usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -o CMakeFiles/Week1.dir/main.c.o   -c /home/herain/Documents/COMP2017/MyTest/Week1/main.c

CMakeFiles/Week1.dir/main.c.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing C source to CMakeFiles/Week1.dir/main.c.i"
	/usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -E /home/herain/Documents/COMP2017/MyTest/Week1/main.c > CMakeFiles/Week1.dir/main.c.i

CMakeFiles/Week1.dir/main.c.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling C source to assembly CMakeFiles/Week1.dir/main.c.s"
	/usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -S /home/herain/Documents/COMP2017/MyTest/Week1/main.c -o CMakeFiles/Week1.dir/main.c.s

CMakeFiles/Week1.dir/pointer.c.o: CMakeFiles/Week1.dir/flags.make
CMakeFiles/Week1.dir/pointer.c.o: ../pointer.c
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/home/herain/Documents/COMP2017/MyTest/Week1/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_2) "Building C object CMakeFiles/Week1.dir/pointer.c.o"
	/usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -o CMakeFiles/Week1.dir/pointer.c.o   -c /home/herain/Documents/COMP2017/MyTest/Week1/pointer.c

CMakeFiles/Week1.dir/pointer.c.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing C source to CMakeFiles/Week1.dir/pointer.c.i"
	/usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -E /home/herain/Documents/COMP2017/MyTest/Week1/pointer.c > CMakeFiles/Week1.dir/pointer.c.i

CMakeFiles/Week1.dir/pointer.c.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling C source to assembly CMakeFiles/Week1.dir/pointer.c.s"
	/usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -S /home/herain/Documents/COMP2017/MyTest/Week1/pointer.c -o CMakeFiles/Week1.dir/pointer.c.s

# Object files for target Week1
Week1_OBJECTS = \
"CMakeFiles/Week1.dir/main.c.o" \
"CMakeFiles/Week1.dir/pointer.c.o"

# External object files for target Week1
Week1_EXTERNAL_OBJECTS =

Week1: CMakeFiles/Week1.dir/main.c.o
Week1: CMakeFiles/Week1.dir/pointer.c.o
Week1: CMakeFiles/Week1.dir/build.make
Week1: CMakeFiles/Week1.dir/link.txt
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --bold --progress-dir=/home/herain/Documents/COMP2017/MyTest/Week1/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_3) "Linking C executable Week1"
	$(CMAKE_COMMAND) -E cmake_link_script CMakeFiles/Week1.dir/link.txt --verbose=$(VERBOSE)

# Rule to build all files generated by this target.
CMakeFiles/Week1.dir/build: Week1

.PHONY : CMakeFiles/Week1.dir/build

CMakeFiles/Week1.dir/clean:
	$(CMAKE_COMMAND) -P CMakeFiles/Week1.dir/cmake_clean.cmake
.PHONY : CMakeFiles/Week1.dir/clean

CMakeFiles/Week1.dir/depend:
	cd /home/herain/Documents/COMP2017/MyTest/Week1/cmake-build-debug && $(CMAKE_COMMAND) -E cmake_depends "Unix Makefiles" /home/herain/Documents/COMP2017/MyTest/Week1 /home/herain/Documents/COMP2017/MyTest/Week1 /home/herain/Documents/COMP2017/MyTest/Week1/cmake-build-debug /home/herain/Documents/COMP2017/MyTest/Week1/cmake-build-debug /home/herain/Documents/COMP2017/MyTest/Week1/cmake-build-debug/CMakeFiles/Week1.dir/DependInfo.cmake --color=$(COLOR)
.PHONY : CMakeFiles/Week1.dir/depend

