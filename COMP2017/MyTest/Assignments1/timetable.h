//
// Created by herain on 3/9/21.
//

#ifndef ASSIGNMENTS1_TIMETABLE_H
#define ASSIGNMENTS1_TIMETABLE_H

#endif //ASSIGNMENTS1_TIMETABLE_H

struct plan;
struct time;
int castStringToTime(const char* time_String, struct time* time_Struct);
int checkArguments(struct plan* plan, int argc, char* args[]);
int endOfAttr(const int* index, const char* sourceString);
int endOfLine(const int* index, const char* sourceString);
int copy(int* index, char* sourceString, char* targetString);
int splitStringToPlan(char* plan_String, struct plan* plan_Struct);
int timeSuitable(struct time* myTime, struct time* planTime);
int testSuitable(struct plan* myPlan, struct plan* tempPlan);
typedef int (*func)(struct time*, struct time*) ;
func timeCompare = timeSuitable;

struct time{
    int hour;
    int minute;
    int second;
};

struct plan{
    char* Source;
    char* Destination;
    struct time arriveTime;
};