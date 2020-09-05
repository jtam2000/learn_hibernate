
USER=`whoami`
export PROJECT_ROOT="/Users/${USER}/Development/Learning"
export PROJECT_NAME=$1
export TERM=xterm-256color
export TERMINAL_EMULATOR=JetBrains-JediTerm


function usage() {

    echo -e 'Usage: source '$(basename ${BASH_SOURCE}) '<project_name>'
    echo -e 'script executes:'
    echo -e '\t 1. mkdir <project_name> in project folder => ' "${PROJECT_ROOT}"
    echo -e '\t 2. git init <project_name>'
    echo -e '\t 3. create <project_name> in github'
    echo -e '\t 4. git push main'
    echo -e '\t 5. cd <project_name>'
}

function start_project() {
    clear

    cd $PROJECT_ROOT
    git init $PROJECT_NAME

    # shellcheck disable=SC2164
    cd $PROJECT_NAME

    # Set up README.me and settings.gradle
    echo -e "# "${PROJECT_NAME} > README.md
    echo -e "rootProject.name =" \'$PROJECT_NAME\' > settings.gradle
    git add README.md settings.gradle
    git commit -m "first commit"

    # create main branch
    git branch -M main

    # create entry on github, use -p for private repository
    hub create -p -c $PROJECT_NAME

    # push the first commit to github
    # and show that the remote was created successfully
    git push --set-upstream origin main
    git remote -v
    git status
    echo -e "\n\n You are at branch:"
    git branch
}



# git branch -M main
# git remote add origin https://github.com/jtam2000/learn_jdbc.git
# git push -u origin master


if [ $# -eq 0 ]; then
    usage
fi

if [ $# -eq 1 ]; then
    start_project $1;
fi