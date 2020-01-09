package com.justa.test.aws.message;

import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClientBuilder;
import com.amazonaws.services.cloudwatch.model.Dimension;
import com.amazonaws.services.cloudwatch.model.MetricDatum;
import com.amazonaws.services.cloudwatch.model.PutMetricDataRequest;
import com.amazonaws.services.cloudwatch.model.PutMetricDataResult;
import com.amazonaws.services.cloudwatch.model.StandardUnit;

// from https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/examples-cloudwatch-publish-custom-metrics.html
public class CustomeMetrics {

	public static void main(String[] args) {

		AmazonCloudWatch cw = AmazonCloudWatchClientBuilder.defaultClient();

		Dimension dimension = new Dimension()
				.withName("UNIQUE_PAGES")
				.withValue("URLS");

		MetricDatum datum = new MetricDatum()
				.withMetricName("PAGES_VISITED")
				.withUnit(StandardUnit.None)
				.withValue(2.0d).withDimensions(dimension);

		PutMetricDataRequest request = new PutMetricDataRequest()
				.withNamespace("SITE/TRAFFIC")
				.withMetricData(datum);

		PutMetricDataResult response = cw.putMetricData(request);

	}
}
