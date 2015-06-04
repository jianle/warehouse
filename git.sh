#!/bin/bash

COMMIT_DESC="$1"
if [ -z "$COMMIT_DESC" ]; then 
    echo "Usage: ./git.sh <commit_desc>"
    exit 1
fi 

git stash
git pull --rebase
git stash pop
git add -A
git commit -m "$COMMIT_DESC"
git push origin master

