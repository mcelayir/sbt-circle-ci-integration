#Introduction

//Write something about circle ci



# Configuration

CircleCI 2.0 requires `config.yml` file to be under
`.circleci` directory which should be under the projects root directory
So the file location should follow `.circleci/config.yml`

To achieve that you can execute following commands in your projects root
directory.

```
> mkdir .circleci/
> touch .circleci/config.yml
```

## config.yml

### version

Circleci config.yml should specify its version in the beginning of the file
so we start with stating the version.

```
version: 2
```

### jobs & build

Before defining the steps that we want to run, first we have to configure circeci
with an entry point and a job to run. For providing endpoint, we can use
`build` keyword and we can create jobs to run under the keyword `jobs`.


## Configuring jobs

### Defining docker images

Docker images needed for the job can be defined under `docker`section as
shown below. Given images will be pulled by circleci.

```
  jobs:
    build:
        docker:
            - image: <<image-name>>
            - image: <<image-name>>
            ...
```

Since we are going to build a sbt project, we can use official docker
image for `openjdk8`

```
  jobs:
    build:
        docker:
            - image: openjdk:8
```

### Defining environment variables

Values that you want to use during the execution of the job 
should be defined under `environment` section as shown below.

```
jobs:
    build:
        environment:
            - MY_VAR_1: <<some-value>>
            - MY_VAR_2: <<some-value>>
```

Since we need sbt, we can set the version that we want to download 
as an environment variable. (yes we are going to download that :sweat_smile:) 

```
jobs:
    build:
        environment:
            - SBT_VERSION: 1.2.8
```

### Setting up steps

We can define each command that we want to run under `steps` section.
Commands can be defined by `run keyword`.

```
jobs:
    build:
        steps:
            - run: <<command>>
            - run: <<command>>
```

Also a name can be defined for a command alongside the actual command.

Multiline commands can be defined by using a pipe `|` at the beginning
of the command

```
jobs:
    build:
        steps:
            - run: 
                name: Say hello
                command: |
                          apt update && apt install -y curl
                          apt-get update
                          apt-get install -y sbt python-pip git
                          ....                        
```

All the given commands will run inside the docker container for the job.

Now we can start defining the steps needed to compile and execute other
commands for our project.

### Installing the sbt

Right now we have an empty container and to run our jobs we have to install
the tools that we need.

To install the sbt, first we have to install curl to be able to make rest
calls. By using curl we are going to download the binary for sbt and install it
We can define all the commands in one step by using the pipe `|` as described
above

```
steps:
    - run:
        name: Get sbt binary
        command: |
                 apt update && apt install -y curl
                 curl -L -o sbt-$SBT_VERSION.deb https://dl.bintray.com/sbt/debian/sbt-$SBT_VERSION.deb
                 dpkg -i sbt-$SBT_VERSION.deb
                 rm sbt-$SBT_VERSION.deb

```

One thing to notice here is that, we read the version value from the environment
variable that we defined earlier by saying `$SBT_VERSION`

### Checking out the code

For circleci to git checkout our code we can use the `- checkout` keyword
as a step. The project will be cloned into container

```
steps:
    - run: ...
    - run: ...
    - checkout  
    ...
```

### Running sbt commands

Now we have our code ready and sbt installed, we can define the steps to 
clean, compile and test our project

```
steps:
    - run:
        name: Clean
        command: sbt clean
    - run:
        name: Compile
        command: sbt compile
    - run:
        name: Test
        command: sbt test
```


#Setting up Your Build on CircleCI

At this point I assume that you have a github repository for your project. If you
don't then create one and push your code to that repository.

Now, navigate to <a>https://circleci.com</a> and signup. You can also select
`Start with github` option.

Go to add project section. Be sure that your github account is selected in 
the dropdown on the top-left of the page. You can click the `Set Up Project`
button next the project that you want to select.

In the next page, you will see circleci will try to give you a config.yml file
and instructions to add the file to your project (that's why circleci is the man).
Since we have configuration ready, we can click on `Start Building` button
which truely reflects our purpose.



#workflows

#building and deploying docker images

#disabling something for branches
