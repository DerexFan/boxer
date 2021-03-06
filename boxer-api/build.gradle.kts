
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.bmuschko.gradle.docker.tasks.image.DockerBuildImage

plugins {
	id("org.springframework.boot") version "2.3.0.RELEASE"
	id("io.spring.dependency-management") version "1.0.9.RELEASE"
	id("com.bmuschko.docker-remote-api") version "6.4.0"
	kotlin("jvm") version "1.3.72"
	kotlin("plugin.spring") version "1.3.72"

}

group = "com.github.sawied.boxer"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
	mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "1.8"
	}
}

tasks.register<Copy>("unzipBootJar"){
	dependsOn(tasks.getByName("bootJar"))
	val outputs=tasks.getByName<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar").outputs
	from(zipTree(outputs.files.first()),"Dockerfile")
	into("$buildDir/docker")
}

docker {
	val host:String = System.getenv("DOCKER_HOST")?:"127.0.0.1"
	url.set("https://$host:2375")
	/*certPath.set(File(System.getProperty("user.home"), ".boot2docker/certs/boot2docker-vm"))
	registryCredentials {
		url.set("https://index.docker.io/v1/")
		username.set("bmuschko")
		password.set("pwd")
		email.set("benjamin.muschko@gmail.com")
	}*/
}

tasks.create("buildDockerImage",DockerBuildImage::class) {
	group = "application"
	description = "build a docker image from unzip directory by dockerfile."

	dependsOn("unzipBootJar")
	inputDir.set(file("$buildDir/docker"))
	images.add("sawied/boxer:latest")
}
