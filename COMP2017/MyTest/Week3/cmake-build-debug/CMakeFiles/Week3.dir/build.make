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
CMAKE_SOURCE_DIR = /home/herain/Documents/COMP2017/MyTest/Week3

# The top-level build directory on which CMake was run.
CMAKE_BINARY_DIR = /home/herain/Documents/COMP2017/MyTest/Week3/cmake-build-debug

# Include any dependencies generated for this target.
include CMakeFiles/Week3.dir/depend.make

# Include the progress variables for this target.
include CMakeFiles/Week3.dir/progress.make

# Include the compile flags for this target's objects.
include CMakeFiles/Week3.dir/flags.make

CMakeFiles/Week3.dir/main.c.o: CMakeFiles/Week3.dir/flags.make
CMakeFiles/Week3.dir/main.c.o: ../main.c
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/home/herain/Documents/COMP2017/MyTest/Week3/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_1) "Building C object CMakeFiles/Week3.dir/main.c.o"
	/usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -o CMakeFiles/Week3.dir/main.c.o   -c /home/herain/Documents/COMP2017/MyTest/Week3/main.c

CMakeFiles/Week3.dir/main.c.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing C source to CMakeFiles/Week3.dir/main.c.i"
	/usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -E /home/herain/Documents/COMP2017/MyTest/Week3/main.c > CMakeFiles/Week3.dir/main.c.i

CMakeFiles/Week3.dir/main.c.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling C source to assembly CMakeFiles/Week3.dir/main.c.s"
	/usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -S /home/herain/Documents/COMP2017/MyTest/Week3/main.c -o CMakeFiles/Week3.dir/main.c.s

# Object files for target Week3
Week3_OBJECTS = \
"CMakeFiles/Week3.dir/main.c.o"

# External object files for target Week3
Week3_EXTERNAL_OBJECTS =

Week3: CMakeFiles/Week3.dir/main.c.o
Week3: CMakeFiles/Week3.dir/build.make
Week3: CMakeFiles/Week3.dir/link.txt
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --bold --progress-dir=/home/herain/Documents/COMP2017/MyTest/Week3/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_2) "Linking C executable Week3"
	$(CMAKE_COMMAND) -E cmake_link_script CMakeFiles/Week3.dir/link.txt --verbose=$(VERBOSE)

# Rule to build all files generated by this target.
CMakeFiles/Week3.dir/build: Week3

.PHONY : CMakeFiles/Week3.dir/build

CMakeFiles/Week3.dir/clean:
	$(CMAKE_COMMAND) -P CMakeFiles/Week3.dir/cmake_clean.cmake
.PHONY : CMakeFiles/Week3.dir/clean

CMakeFiles/Week3.dir/depend:
	cd /home/herain/Documents/COMP2017/MyTest/Week3/cmake-build-debug && $(CMAKE_COMMAND) -E cmake_depends "Unix Makefiles" /home/herain/Documents/COMP2017/MyTest/Week3 /home/herain/Documents/COMP2017/MyTest/Week3 /home/herain/Documents/COMP2017/MyTest/Week3/cmake-build-debug /home/herain/Documents/COMP2017/MyTest/Week3/cmake-build-debug /home/herain/Documents/COMP2017/MyTest/Week3/cmake-build-debug/CMakeFiles/Week3.dir/DependInfo.cmake --color=$(COLOR)
.PHONY : CMakeFiles/Week3.dir/depend

