# Komunumo

[![All Tests](https://github.com/McPringle/komunumo/actions/workflows/all-tests.yml/badge.svg)](https://github.com/McPringle/komunumo/actions/workflows/all-tests.yml)
[![codecov](https://codecov.io/github/McPringle/komunumo/graph/badge.svg?token=6MTYWYK083)](https://codecov.io/github/McPringle/komunumo)

**Open Source Community Manager**

*Komunumo* is an esperanto noun with a meaning of *community*.

## Architecture

The server of *Komunumo* is written using the [Java programming language](https://en.wikipedia.org/wiki/Java_(programming_language)). The main framework is [Spring](https://spring.io/). For the user interface, we use [Vaadin Flow](https://vaadin.com/flow). To access the database, we rely on [jOOQ](https://www.jooq.org/). To coordinate the build process, [Maven](https://maven.apache.org/) is used.

### Structure

Vaadin web applications are full-stack and include both client-side and server-side code in the same project.

| Directory                                  | Description                                 |
|:-------------------------------------------|:--------------------------------------------|
| `src/main/frontend/`                       | Client-side source directory                |
| &nbsp;&nbsp;&nbsp;&nbsp;`index.html`       | HTML template                               |
| &nbsp;&nbsp;&nbsp;&nbsp;`index.ts`         | Frontend entrypoint                         |
| &nbsp;&nbsp;&nbsp;&nbsp;`main-layout.ts`   | Main layout Web Component (optional)        |
| &nbsp;&nbsp;&nbsp;&nbsp;`views/`           | UI views Web Components (TypeScript / HTML) |
| &nbsp;&nbsp;&nbsp;&nbsp;`styles/`          | Styles directory (CSS)                      |
| `src/main/java/org/komunumo/`              | Server-side source directory                |
| &nbsp;&nbsp;&nbsp;&nbsp;`Application.java` | Server entrypoint                           |
| &nbsp;&nbsp;&nbsp;&nbsp;`AppShell.java`    | application-shell configuration             |

### Useful Vaadin Links

- Read the documentation at [vaadin.com/docs](https://vaadin.com/docs).
- Follow the tutorials at [vaadin.com/tutorials](https://vaadin.com/tutorials).
- Watch training videos and get certified at [vaadin.com/learn/training](https://vaadin.com/learn/training).
- Create new projects at [start.vaadin.com](https://start.vaadin.com/).
- Search UI components and their usage examples at [vaadin.com/components](https://vaadin.com/components).
- View use case applications that demonstrate Vaadin capabilities at [vaadin.com/examples-and-demos](https://vaadin.com/examples-and-demos).
- Discover Vaadin's set of CSS utility classes that enable building any UI without custom CSS in the [docs](https://vaadin.com/docs/latest/ds/foundation/utility-classes).
- Find a collection of solutions to common use cases in [Vaadin Cookbook](https://cookbook.vaadin.com/).
- Find Add-ons at [vaadin.com/directory](https://vaadin.com/directory).
- Ask questions on [Stack Overflow](https://stackoverflow.com/questions/tagged/vaadin) or join our [Discord channel](https://discord.gg/MYFq5RTbBn).
- Report issues, create pull requests in [GitHub](https://github.com/vaadin/platform).

## Configuration

The file `application.properties` contains only some default values. To override the default values and to specify other configuration options, just set them as environment variables. The following sections describe all available configuration options. You only need to specify these options if your configuration settings differ from the defaults.

### Server

The server runs on port 8080 by default. If you don't like it, change it:

```
PORT=8080
```

### Database

*Komunumo* needs a database to store the business data. By default, *Komunumo* comes with [MariaDB](https://mariadb.org/) drivers. MariaDB is recommended because we are using it during development, and it is highly tested with *Komunumo*. All free and open source JDBC compatible databases are supported, but you need to configure the JDBC driver dependencies accordingly. Please make sure that your database is using a Unicode character set to avoid problems storing data containing Unicode characters.

The `DB_USER` is used to access the *Komunumo* database including automatic schema migrations and needs `ALL PRIVILEGES`.

```
DB_URL=jdbc:mariadb://localhost:3306/komunumo?serverTimezone\=Europe/Zurich&allowMultiQueries=true
DB_USER=johndoe
DB_PASS=verysecret
```

The database schema will be migrated automatically by *Komunumo*.

#### Important MySQL and MariaDB configuration

MySQL and MariaDB have a possible silent truncation problem with the `GROUP_CONCAT` command. To avoid this it is necessary, to configure these two databases to allow multi queries. Just add `allowMultiQueries=true` to the JDBC database URL like in this example (you may need to scroll the example code to the right):

```
DB_URL=jdbc:mariadb://localhost:3306/komunumo?serverTimezone\=Europe/Zurich&allowMultiQueries=true
```

### Matenet Database

The old [website](https://www.jug.ch/) runs on a server at [Metanet](https://www.metanet.ch/). All the existing data needs to be imported to *Komunumo* once at go-live. This is done by querying the old database. To run the import, the following configurations need to be set:

```
METANET_DATABASE_URL=jdbc:mariadb://remotehost:3306/database?serverTimezone\=Europe/Zurich&allowMultiQueries=true
METANET_DATABASE_USER=janedoe
METANET_DATABASE_PASSWORD=verysecred
```

When these settings are not blank, the import will start automatically ten seconds after the launch of *Komunumo*. 

### Website

Every website needs some basic configuration. Have a look at the following configuration options, most of which are self-describing:

```
WEBSITE_URL=http://localhost:8080
WEBSITE_ASSOCIATION=User Group Name
WEBSITE_ABOUT_TEXT=Tell the world what this user group is about.
WEBSITE_CONTACT_ADDRESS=12345 Foobar
WEBSITE_CONTACT_EMAIL=noreply@localhost
WEBSITE_COPYRIGHT=© User Group Name
WEBSITE_LOGO_TEMPLATE=http://localhost/images/logo_%02d.png
WEBSITE_LOGO_MIN=1
WEBSITE_LOGO_MAX=10
```

> [!NOTE]
> The configuration option `WEBSITE_ABOUT_TEXT` is allowed to contain HTML code! So you can use paragraphs and links, if needed.

> [!IMPORTANT]
> The logo handling is very special: If you want to specify only one logo, just use the URL and set the `MIN` and `MAX` values to `0`. Done. But if you want *Komunumo* to pick a random logo on every request, you can specify a logo template which must contain a String format argument for a decimal integer representation (like `%02d` for a two-digit number in the example above). In addition, set the `MIN` and `MAX` values as needed. *Komunumo* will randomly pick a number between the provided `MIN` and `MAX` values (both included) and merge it with the provided `TEMPLATE` to create the image URL.

## Build

### Maven

*Komunumo* uses [Maven](https://maven.apache.org/) to build the project. Please use standard Maven commands to build what you need:

| Command          | What it does                                                      |
|------------------|-------------------------------------------------------------------|
| `./mvnw`         | compile and run the app                                           |
| `./mvnw clean`   | cleanup generated files and build artefacts                       |
| `./mvnw compile` | compile the code without running the tests                        |
| `./mvnw test`    | compile and run all tests                                         |
| `./mvnw package` | compile, test, and create a JAR file to run it with Java directly |
| `./mvnw verify`  | compile, test, package, and run analysis tools                    |

There is *no need* to run the `install` or `deploy` tasks. They will just run longer, produce unnecessary output, burn energy, and occupy your disk space. [Don't just blindly run mvn clean install...](https://www.andreaseisele.com/posts/mvn-clean-install/)

### Docker

*Komunumo* comes with a complete dockerized build for production use. It is not recommended to use the self-contained build for development purposes. Please take a look at the section about [Production Build](#production-build) below.

## Running and debugging

There are two ways to run the application: From the command line or directly from your IDE.

### Running the server from the command line.

To run from the command line, use `./mvnw` and open http://localhost:8080 in your browser.

### Running and debugging the server in Intellij IDEA

#### Using Maven

- On the right side of the window, select "Maven" --> "Plugins" --> `spring-boot` --> `spring-boot:run`
- Optionally, you can disable tests by clicking on a `Skip Tests mode` blue button.

After the server has started, you can view it at http://localhost:8080/ in your browser.
You can now also attach breakpoints in code for debugging purposes, by clicking next to a line number in any source file.

#### Using the main method

- Locate the `Application.java` class in the Project view. It is in the `src` folder, under the main package's root.
- Right-click on the Application class
- Select "Debug 'Application.main()'" from the list

After the server has started, you can view it at http://localhost:8080/ in your browser.
You can now also attach breakpoints in code for debugging purposes, by clicking next to a line number in any source file.

### Running and debugging the server in Eclipse

#### Using Maven

- Right click on a project folder and select `Run As` --> `Maven build...`. After that a configuration window is opened.
- In the window set the value of the **Goals** field to `spring-boot:run`
- You can optionally select the `Skip tests` checkbox
- All the other settings can be left to default

Once configurations are set clicking `Run` will start the application.

Do not worry if the debugger breaks at a `SilentExitException`. This is a Spring Boot feature and happens on every startup.

After the server has started, you can view it at http://localhost:8080/ in your browser.
You can now also attach breakpoints in code for debugging purposes, by clicking next to a line number in any source file.

#### Using the main method

- Locate the `Application.java` class in the Package Explorer. It is in `src/main/java`, under the main package.
- Right-click on the file and select `Debug As` --> `Java Application`.

Do not worry if the debugger breaks at a `SilentExitException`. This is a Spring Boot feature and happens on every startup.

After the server has started, you can view it at http://localhost:8080/ in your browser.
You can now also attach breakpoints in code for debugging purposes, by clicking next to a line number in any source file.

## Running using Docker

To build the Dockerized version of the project, run

```
docker build . -t komunumo:latest
```

Once the Docker image is correctly built, you can test it locally using

```
docker run -p 8080:8080 komunumo:latest
```

## Communication

### Matrix Chat

There is a channel at Matrix for quick and easy communication. This is publicly accessible for everyone. For developers as well as users. The communication in this chat is to be regarded as short-lived and has no documentary character.

You can find our Matrix channel here: [@komunumo:ijug.eu](https://matrix.to/#/%23komunumo:ijug.eu)

### GitHub Discussions

We use the corresponding GitHub function for discussions. The discussions held here are long-lived and divided into categories for the sake of clarity. One important category, for example, is that for questions and answers.

Discussions on GitHub: https://github.com/McPringle/komunumo/discussions  
Questions and Answers: https://github.com/McPringle/komunumo/discussions/categories/q-a

## Contributing

### Good First Issues

To find possible tasks for your first contribution to *Komunumo*, we tagged some of the hopefully easier to solve issues as [good first issue](https://github.com/McPringle/komunumo/labels/good%20first%20issue).

If you prefer to meet people in real life to contribute to *Komunumo* together, we recommend to visit a [Hackergarten](https://www.hackergarten.net/) event. *Komunumo* is often selected as a contribution target in [Lucerne](https://www.meetup.com/hackergarten-luzern/), [Zurich](https://www.meetup.com/hackergarten-zurich/), and at the [JavaLand](https://www.javaland.eu/) conference.

Please join our developer community using our [Matrix chat](#matrix-chat) to get support and help for contributing to *Komunumo*.

### Sign-off your commits

It is important to sign-off *every* commit. That is a de facto standard way to ensure that *you* have the right to submit your content and that you agree to the [DCO](DCO.md) (Developer Certificate of Origin).

You can find more information about why this is important and how to do it easily in a very good [blog post](https://dev.to/janderssonse/git-signoff-and-signing-like-a-champ-41f3)  by Josef Andersson.

### Add an emoji to your commit

We love to add an emoji to the beginning of every commit message which relates to the nature of the change. You can find a searchable list of possible emojis and their meaning in the overview on the [gitmoji](https://gitmoji.dev/) website. If you prefer, you can also install one of the plugins that are available for almost all common IDEs.

### AI Generated Code

AI generated source code is based on real existing source code, which is copied in whole or in part into the generated code. The license of the original source code with which the AI was trained is not taken into account. It is not clear which license conditions apply and how these can be complied with. For legal reasons, we therefore do not allow AI-generated source code at all.

## Contributors

Special thanks for all these wonderful people who had helped this project so far ([emoji key](https://allcontributors.org/docs/en/emoji-key)):

<!-- ALL-CONTRIBUTORS-LIST:START - Do not remove or modify this section -->
<!-- prettier-ignore-start -->
<!-- markdownlint-disable -->
<table>
  <tbody>
    <tr>
      <td align="center" valign="top" width="14.28%"><a href="https://github.com/McPringle"><img src="https://avatars.githubusercontent.com/u/1254039?v=4?s=100" width="100px;" alt="Marcus Fihlon"/><br /><sub><b>Marcus Fihlon</b></sub></a><br /><a href="#projectManagement-McPringle" title="Project Management">📆</a> <a href="#ideas-McPringle" title="Ideas, Planning, & Feedback">🤔</a> <a href="https://github.com/McPringle/komunumo/commits?author=McPringle" title="Code">💻</a> <a href="#design-McPringle" title="Design">🎨</a></td>
      <td align="center" valign="top" width="14.28%"><a href="https://github.com/Interactiondesigner"><img src="https://avatars.githubusercontent.com/u/17220369?v=4?s=100" width="100px;" alt="Interactiondesigner"/><br /><sub><b>Interactiondesigner</b></sub></a><br /><a href="#design-Interactiondesigner" title="Design">🎨</a></td>
      <td align="center" valign="top" width="14.28%"><a href="https://github.com/knoobie"><img src="https://avatars.githubusercontent.com/u/3968629?v=4?s=100" width="100px;" alt="Knoobie"/><br /><sub><b>Knoobie</b></sub></a><br /><a href="https://github.com/McPringle/komunumo/commits?author=knoobie" title="Code">💻</a></td>
      <td align="center" valign="top" width="14.28%"><a href="https://github.com/shreegilliorkar"><img src="https://avatars.githubusercontent.com/u/70030205?v=4?s=100" width="100px;" alt="Shree Gillorkar"/><br /><sub><b>Shree Gillorkar</b></sub></a><br /><a href="https://github.com/McPringle/komunumo/commits?author=shreegilliorkar" title="Code">💻</a></td>
    </tr>
  </tbody>
</table>

<!-- markdownlint-restore -->
<!-- prettier-ignore-end -->

<!-- ALL-CONTRIBUTORS-LIST:END -->

## Copyright and License

[AGPL License](https://www.gnu.org/licenses/agpl-3.0.de.html)

*Copyright (C) Marcus Fihlon and the individual contributors to **Komunumo**.*

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
