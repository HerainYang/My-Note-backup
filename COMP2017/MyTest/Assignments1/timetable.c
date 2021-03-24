#include <stdio.h>
#include <zconf.h>
#include "timetable.h"
#define MAXSTRINGSIZE 4096

/**
 * Transfer string into time format.
 * @param time_String
 * @param time_Struct
 * @return -1 means time format is wrong or it is not a current time.
 */
int castStringToTime(const char* time_String, struct time* time_Struct){
    if(time_String[8] != '\0' && time_String[8] != '\r' && time_String[8] != '\n')
        return -1;
    time_Struct->hour = (time_String[0] - '0') * 10 + (time_String[1] - '0');
    time_Struct->minute = (time_String[3] - '0') * 10 + (time_String[4] - '0');
    time_Struct->second = (time_String[6] - '0') * 10 + (time_String[7] - '0');
    if(time_Struct->hour > 59 || time_Struct->hour < 0)
        return -1;
    if(time_Struct->minute > 59 || time_Struct->minute < 0)
        return -1;
    if(time_Struct->second > 59 || time_Struct->second < 0)
        return -1;
    return 0;
}

/**
 * Test if the input format is correct
 * @param plan
 * @param argc
 * @param args
 * @return -1 means it is wrong, 0 means correct.
 */
int checkArguments(struct plan* plan, int argc, char* args[]){
    if(argc != 4)
    {
        printf("Please provide <source> <destination> <time> as command line arguments\n");
        return -1;
    }
    plan->Source = args[1];
    plan->Destination = args[2];
    if(castStringToTime(args[3], &plan->arriveTime) == -1){
        printf("> Argument <3> has a wrong format of time, please enter time as hh:mm:ss\n");
        return -1;
    }
    return 0;
}

/**
 * Test if the current input reach the end of the attribute.
 * @param index
 * @param sourceString
 */
int endOfAttr(const int* index, const char* sourceString){
    return sourceString[*index] == ':' && sourceString[*index + 1] == ':';
}

/**
 * Test if the current input reach the end of the line.
 * @param index
 * @param sourceString
 */
int endOfLine(const int* index, const char* sourceString){
    return sourceString[*index] != '\0';
}

/**
 * @param index
 * @param sourceString
 * @param targetString
 * @return -1 when the format of input error, 0 when success
 */
int copy(int* index, char* sourceString, char* targetString){
    int i_target = 0;
    for (; !endOfAttr(index, sourceString); (*index)++, i_target++){
        if(sourceString[i_target] == '\0') {
            return -1;
        }
        targetString[i_target] = sourceString[*index];
    }
    targetString[i_target] = '\0';
    return 0;
}

/**
 * Transfer string format into struct plan
 * @param plan_String
 * @param plan_Struct
 * @return -1 means the format of file is wrong, 1 means success.
 */
int splitStringToPlan(char* plan_String, struct plan* plan_Struct){
    int index = 0;
    if(copy(&index, plan_String, plan_Struct->Source) == -1) {
        return -1;
    }
    index += 2;
    if(copy(&index, plan_String, plan_Struct->Destination) == -1) {
        return -1;
    }
    index += 2;
    if(castStringToTime(&plan_String[index], &plan_Struct->arriveTime) == -1){
        return -1;
    }
    if(!endOfLine(&index, plan_String))
        return -1;
    return 1;
}

/**
 * Test if myTime is earlier than planTime
 * @param myTime
 * @param planTime
 * @return -1 it is not, 1 it is.
 */
int timeSuitable(struct time* myTime, struct time* planTime){
    if(myTime->hour > planTime->hour) {
        return -1;
    } else if (myTime->hour == planTime->hour){
        if(myTime->minute > planTime->minute) {
            return -1;
        } else if (myTime->minute == planTime->minute){
            if(myTime->second > planTime->second) {
                return -1;
            } else {
                return 1;
            }
        }
    }
    return 1;

}

/**
 * Test if tempPlan suitable for myPlan.
 * @param myPlan
 * @param tempPlan
 * @return -1 means not suitable, 1 means suitable.
 */
int testSuitable(struct plan* myPlan, struct plan* tempPlan){
    int i;
    for(i = 0; myPlan->Source[i]!='\0'; i++){
        if(myPlan->Source[i] != tempPlan->Source[i])
            return -1;
    }
    if(tempPlan->Source[i] != '\0')
        return -1;

    for(i = 0; myPlan->Destination[i]!='\0'; i++){
        if(myPlan->Destination[i] != tempPlan->Destination[i])
            return -1;
    }
    if(tempPlan->Destination[i] != '\0')
        return -1;

    if(timeSuitable(&myPlan->arriveTime, &tempPlan->arriveTime) == -1){
        return -1;
    }

    return 1;
}

int main(int argc, char* args[]) {
    struct plan myPlan;
    struct plan tempPlan;
    char anything[MAXSTRINGSIZE];
    char tempSource[1000];
    tempPlan.Source = tempSource;
    char tempDestination[1000];
    tempPlan.Destination = tempDestination;
    int flag;
    int line = 0;

    struct time nextTime = {60, 60, 60};


    if(checkArguments(&myPlan, argc, args) == -1){
        return 0;
    }

    if(isatty(0) == 1){
        printf("> Please input data using redirect\n");
        return 0;
    }

    while(fgets(anything, 4096, stdin) != NULL){
        line++;
        flag = splitStringToPlan(anything, &tempPlan);
        if(flag == -1)
        {
            printf("> There is a wrong format in line %d, please check the input file\n", line);
            continue;
        } else if (flag == -2){
            printf("> There is a wrong format of time in line %d, please enter time as hh:mm:ss", line);
        }
        
        if(testSuitable(&myPlan, &tempPlan) == 1){
            if(timeSuitable(&tempPlan.arriveTime, &nextTime) == 1){
                nextTime.hour = tempPlan.arriveTime.hour;
                nextTime.minute = tempPlan.arriveTime.minute;
                nextTime.second = tempPlan.arriveTime.second;
            }
        }
    }
    if(nextTime.hour == 60){
        printf("No suitable trains can be found\n");
    } else {
        printf("The next train to %s from %s departs at %02d:%02d:%02d\n",  myPlan.Destination, myPlan.Source, nextTime.hour, nextTime.minute, nextTime.second);
    }

    return 0;
}